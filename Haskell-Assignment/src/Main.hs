module Main where

import Args
  ( AddOptions (..),
    Args (..),
    GetOptions (..),
    SearchOptions (..),
    parseArgs,
  )
import qualified Data.List as L
import qualified Entry.DB as DBB
import Entry.Entry
  ( Entry (..),
    FmtEntry (FmtEntry),
    matchedByAllQueries,
    matchedByQuery,
  )
import Result
import System.Environment (getArgs)
import Test.SimpleTest.Mock
import Prelude hiding (print, putStrLn, readFile,writeFile)
import qualified Prelude
import qualified Data.ByteString as DB
import qualified Entry.DB as DBB

usageMsg :: String
usageMsg =
  L.intercalate
    "\n"
    [ "snip - code snippet manager",
      "Usage: ",
      "snip add <filename> lang [description] [..tags]",
      "snip search [code:term] [desc:term] [tag:term] [lang:term]",
      "snip get <id>",
      "snip init"
    ]

-- | Handle the init command
handleInit :: TestableMonadIO m => m ()
handleInit = length (DBB.serialize DBB.empty) `seq` writeFile "snippets.ben" (DBB.serialize DBB.empty)

displayError :: TestableMonadIO m => String -> m ()
displayError errorMessage = putStrLn errorMessage

-- | Handle the get command
handleGet :: TestableMonadIO m => GetOptions -> m ()
handleGet getOpts = do
  result <- DBB.load  -- Call the load function to get the result
  case result of
    Error de -> displayError "Failed to load DB"
    Success entries ->
      case DBB.findFirst (\entry -> entryId entry == getOptId getOpts) entries of
        Nothing -> putStrLn "No such entry!"
        Just entry -> putStrLn (entrySnippet entry)
  

-- | Handle the search command
handleSearch :: TestableMonadIO m => SearchOptions -> m ()
handleSearch searchOpts =  do
  result <- DBB.load
  case result of
    Error _-> putStrLn "Failed to load DB"
    Success snippet ->
      let 
        searchResult [] = putStrLn "No entries found"
        searchResult (x:[]) = putStrLn (show (FmtEntry x))
        searchResult (x:xs)=do
          putStrLn (show (FmtEntry x)) 
          searchResult xs
      in
        searchResult (DBB.findAll (\entry -> Entry.Entry.matchedByAllQueries (searchOptTerms searchOpts) entry) snippet)
  

-- | Handle the add command
handleAdd :: TestableMonadIO m => AddOptions -> m ()
handleAdd addOpts =  do
  content <- readFile (addOptFilename addOpts)
  dbLoad <- DBB.load
  case dbLoad of
    Error _ -> putStrLn "Failed to load DB"
    Success snippet -> do
      case DBB.findFirst (\entry -> (entrySnippet entry) == content) snippet of
        Just entry -> do
          putStrLn "Entry with this content already exists: "
          putStrLn (show (FmtEntry entry))
        Nothing -> do
          modif <- DBB.modify $ DBB.insertWith (\id -> makeEntry id content addOpts)
          case modif of
            Error _ -> putStrLn "Failed to modify DB"
            Success _ -> return ()
  return ()
  where
    makeEntry :: Int -> String -> AddOptions -> Entry
    makeEntry id snippet addOpts =
      Entry
        { entryId = id,
          entrySnippet = snippet,
          entryFilename = addOptFilename addOpts,
          entryLanguage = addOptLanguage addOpts,
          entryDescription = addOptDescription addOpts,
          entryTags = addOptTags addOpts
        }
  

-- | Dispatch the handler for each command
run :: TestableMonadIO m => Args -> m ()
run (Add addOpts) = handleAdd addOpts
run (Search searchOpts) = handleSearch searchOpts
run (Get getOpts) = handleGet getOpts
run Init = handleInit
run Help = putStrLn usageMsg

main :: IO ()
main = do
  args <- getArgs
  let parsed = parseArgs args
  case parsed of
    (Error err) -> Prelude.putStrLn usageMsg
    (Success args) -> run args
