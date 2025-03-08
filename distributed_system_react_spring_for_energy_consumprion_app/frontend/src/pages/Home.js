import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link, useParams } from "react-router-dom";
import Navbar from "../layout/Navbar";

const ChatButton = ({ currentUserId, otherUserId }) => (
    <Link
        className="btn btn-success mx-2"
        to={`/chat/${currentUserId}/${otherUserId}`}
    >
        Chat
    </Link>
);

export default function Home() {
    const [users, setUsers] = useState([]);
    const [devices, setDevices] = useState([]);
    const [error, setError] = useState(null);
    const { id } = useParams();
    const user_id = Number(id);

    useEffect(() => {
        loadUsers();
        loadDevices();
    }, []);

    const loadUsers = async () => {
        try { //http://localhost:8083
            const token = localStorage.getItem("token");
            const result = await axios.get(`http://localhost/utilizatorapp/users`, {
                headers: { Authorization: `Bearer ${token}` },
                withCredentials: true,
            });
            setUsers(result.data);
        } catch (err) {
            console.error("Error loading users:", err);
            setError("Failed to load users. Please try again.");
        }
    };

    const deleteUser = async (userId) => {
        try { //http://localhost:8083
            const token = localStorage.getItem("token");
            await axios.delete(`http://localhost/utilizatorapp/user/${userId}`, {
                headers: { Authorization: `Bearer ${token}` },
                withCredentials: true,
            });
            loadUsers();
        } catch (err) {
            console.error("Error deleting user:", err);
            setError("Failed to delete user. Please try again.");
        }
    };

    const loadDevices = async () => {
        try {
            //http://localhost:8081
            const result = await axios.get(`http://localhost/dispozitiveapp/all_devices`);
            setDevices(result.data);
        } catch (err) {
            console.error("Error loading device:", err);
            setError("Failed to load devices. Please try again.");
        }
    };

    const deleteDevice = async (deviceId) => {
        try { //http://localhost:8081
            //const token = localStorage.getItem("token");
            await axios.delete(`http://localhost/dispozitiveapp/device/${deviceId}`, {
              //  headers: { Authorization: `Bearer ${token}` },
               // withCredentials: true,
            });
            loadDevices();
        } catch (err) {
            console.error("Error deleting device:", err);
            setError("Failed to delete device. Please try again.");
        }
    };

    return (
        <div className="app">
            <div className="page">
                <Navbar/>
                <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
                    <Link className="btn btn-outline-light" to={`/adduser/${user_id}`}>Add user</Link>
                    <Link className="btn btn-outline-light" to={`/adddevice/${user_id}`}>Add device</Link>
                </nav>
                <div className="container">
                    {error && <div className="alert alert-danger">{error}</div>}
                    <div className='py-4'>
                        <h2>Users</h2>
                        <table className="table border shadow">
                            <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope='col'>First Name</th>
                                <th scope='col'>Last Name</th>
                                <th scope="col">Age</th>
                                <th scope="col">Username</th>
                                <th scope='col'>Role</th>
                                <th scope="col">Action</th>
                                <th scope="col">Chat</th>
                            </tr>
                            </thead>
                            <tbody>
                            {users.map((user) => (
                                <tr key={user.userId}>
                                    <th scope="row">{user.userId}</th>
                                    <td>{user.firstName}</td>
                                    <td>{user.lastName}</td>
                                    <td>{user.age}</td>
                                    <td>{user.username}</td>
                                    <td>{user.role}</td>
                                    <td>
                                        <Link className="btn btn-primary mx-2"
                                              to={`/viewuser/${user.userId}/${user_id}`}>View</Link>
                                        <Link className="btn btn-outline-primary mx-2"
                                              to={`/edituser/${user.userId}/${user_id}`}>Edit</Link>
                                        <button className="btn btn-danger mx-2"
                                                onClick={() => deleteUser(user.userId)}>Delete
                                        </button>
                                    </td>
                                    <td>
                                        <ChatButton currentUserId={user_id} otherUserId={user.userId}/>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                </div>
                <br/>
                <br/>
                <br/>
                <div className="container">
                    {error && <div className="alert alert-danger">{error}</div>}
                    <div className='py-4'>
                        <h2>Devices</h2>
                        <table className="table border shadow">
                            <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope='col'>Description</th>
                                <th scope='col'>Address</th>
                                <th scope="col">Mhec</th>
                                <th scope="col">User id</th>
                                <th scope="col">Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            {devices.map((dev) => (
                                <tr key={dev.device_id}>
                                    <th scope="row">{dev.device_id}</th>
                                    <td>{dev.description}</td>
                                    <td>{dev.address}</td>
                                    <td>{dev.mhec}</td>
                                    <td>{dev.user_id}</td>
                                    <td>
                                        <Link className="btn btn-primary mx-2"
                                              to={`/viewdevice/${dev.device_id}/${user_id}`}>View</Link>
                                        <Link className="btn btn-outline-primary mx-2"
                                              to={`/editdevice/${dev.device_id}/${user_id}`}>Edit</Link>
                                        <button className="btn btn-danger mx-2"
                                                onClick={() => deleteDevice(dev.device_id)}>Delete
                                        </button>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    );
}
