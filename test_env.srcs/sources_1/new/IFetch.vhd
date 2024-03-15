----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 04/01/2023 04:24:40 PM
-- Design Name: 
-- Module Name: IFetch - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- 
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;


-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity IFetch is
  Port (clk:in STD_LOGIC;
  en:in STD_LOGIC;
  rst:in STD_LOGIC;
  addr_branch:in STD_LOGIC_VECTOR(15 downto 0);
  addr_jump:in STD_LOGIC_VECTOR(15 downto 0);
  Jump:in STD_LOGIC;
  PCSrc:in STD_LOGIC;
  Instruction:out STD_LOGIC_VECTOR(15 downto 0);
  PCinc:out STD_LOGIC_VECTOR(15 downto 0) );
  
end IFetch;

architecture Behavioral of IFetch is
signal adresa:STD_LOGIC_VECTOR(15 downto 0):=(others =>'0');
type tROM is array( 0 to 255) of STD_LOGIC_VECTOR(15 downto 0);
signal ROM:tROM:=(
 B"010_000_100_0000000",
 B"000_000_000_001_0_001",--i=0
 B"000_000_000_010_0_001",
 B"001_000_101_0000001",
 B"100_001_100_0100001",
 B"010_010_011_0000000",
 B"010_010_110_0000001",
 B"010_010_111_0000010",
 B"000_011_011_011_0_010",
 B"000_110_110_110_0_010",
 B"000_111_111_1110_0_010",
 B"000_110_111_110_0_001",
 B"100_011_110_0011000",
 B"010_010_001_00100000",
 B"010_010_110_0000001",
 B"010_010_111_0000010",
 B"000_011_011_011_0_010",
 B"000_110_110_110_0_010",
 B"000_111_111_111_0_010",
 B"000_110_011_110_0_001",
 B"100_110_111_0000100",
 B"000_101_101_101_0_011",
 B"010_010_011_0000000",
 B"001_010_010_0000001",
 B"001_001_001_0000001",
 B"111_0000000000101",
 B"001_010_010_0000001",
 B"001_001_001_0000001",
 B"111_0000000000101",
 B"011_100_101_00000001",
 others=>x"0000"
);
signal muxSrc:STD_lOGIC_VECTOR(15 downto 0);
signal D:STD_LOGIC_VECTOR(15 downto 0);
signal adresa1:STD_LOGIC_VECTOR(15 downto 0);
begin
process(clk)
begin
if(rising_edge(clk)) then
adresa<=D;
end if;
end process;
Instruction<=ROM(conv_integer(adresa));
process(clk)
begin
if(rising_edge(clk)) then
adresa1<=adresa+1;
end if;
end process;
PCinc<=adresa1;
process(PCSrc,addr_branch,adresa1)
begin
if(PCSrc='0') then
muxSrc<=adresa1;
else muxSrc<=addr_branch;
end if;
end process;
process(Jump,addr_jump,muxSrc)
begin
if(Jump='0') then
D<=muxSrc;
else D<=addr_jump;
end if;
end process;
end Behavioral;
