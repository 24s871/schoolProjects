import './App.css';
import "bootstrap/dist/css/bootstrap.min.css";
import Home from "./pages/Home";
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import AddUser from "./users/AddUser";
import EditUser from "./users/EditUser";
import ViewUser from "./users/ViewUser";
import Login from "./login/Login";
import Register from "./login/Register";
import Client from "./pages/Client";
import AddDevice from "./device/AddDevice";
import EditDevice from "./device/EditDevice";
import ViewDevice from "./device/ViewDevice";
import Chart from "./pages/Chart";
import ChatRoom from "./pages/ChatRoom";
import ClientChatPage from "./pages/ClientChatPage";
import AdminChatPage from "./pages/AdminChatPage";
import ChatPage from "./pages/ChatPage";

function App() {
    return (
        <div className="App">
            <Router>
                <Routes>
                    <Route exact path="/" element={<Login/>}/>
                    <Route exact path="/register" element={<Register/>}/>
                    <Route exact path="/client/:id" element={<Client/>}/>
                    <Route exact path="/chat/:userId/:sendId" element={<ChatPage />} />
                    <Route exact path="/chart/:id" element={<Chart/>}/>
                    <Route exact path="/home/:id" element={<Home/>}/>
                    <Route exact path="/adddevice/:homeID" element={<AddDevice/>}/>
                    <Route exact path="/editdevice/:id/:homeID" element={<EditDevice/>}/>
                    <Route exact path="/viewdevice/:id/:homeID" element={<ViewDevice/>}/>
                    <Route exact path="/adduser/:homeID" element={<AddUser/>}/>
                    <Route exact path="/edituser/:id/:homeID" element={<EditUser/>}/>
                    <Route exact path="/viewuser/:id/:homeID" element={<ViewUser/>}/>
                    <Route exact path="/admin-chat/:id" element={<AdminChatPage/>}/>
                </Routes>
            </Router>
        </div>
    );
}

export default App;
