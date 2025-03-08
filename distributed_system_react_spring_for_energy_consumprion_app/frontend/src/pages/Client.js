import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import axios from "axios";
import NavbarClient from "../layout/NavbarClient";

export default function Client() {
    const [devices, setDevices] = useState([]);
    const [admins, setAdmins] = useState(null);
    const { id } = useParams();
    const userId = Number(id);

    useEffect(() => {
        loadDevices();
        loadAdmin();
    }, []);

    const loadDevices = async () => {
        try {
            //http://localhost:8081
            //const token = localStorage.getItem("token");
            const result = await axios.get(`http://localhost/dispozitiveapp/${userId}/devices`, {
                // headers: { Authorization: `Bearer ${token}` },
                // withCredentials: true,
            });
            setDevices(result.data);
        } catch (err) {
            console.error("Error loading device:", err);
        }
    };

    const loadAdmin = async () => {
        try {
            //http://localhost:8083
            //const token = localStorage.getItem("token");
            const result = await axios.get(`http://localhost/utilizatorapp/admin`, {
               // headers: {
                //    Authorization: `Bearer ${token}`
               // }
            });
            setAdmins(result.data);
        } catch (error) {
            console.error("Error loading admin:", error);
        }
    };

    return (
        <div className="app">
            <NavbarClient />
            <Link className="pixie" to={`/chart/${userId}`}>View charts</Link>
            <div className="container m-0">
                <div className='py-5'>
                    <h2>Your Devices</h2>
                    <table className="table border shadow">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope='col'>Description</th>
                            <th scope='col'>Address</th>
                            <th scope="col">Maximum hourly energy consumption</th>
                        </tr>
                        </thead>
                        <tbody>
                        {devices.map((device) => (
                            <tr key={device.device_id}>
                                <th scope="row">{device.device_id}</th>
                                <td>{device.description}</td>
                                <td>{device.address}</td>
                                <td>{device.mhec}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>

                    <h2>Admin Contact</h2>
                    {admins && (
                        <table className="table border shadow">
                            <thead>
                            <tr>
                                <th scope='col'>Admin ID</th>
                                <th scope='col'>First Name</th>
                                <th scope='col'>Last Name</th>
                                <th scope='col'>Chat</th>
                            </tr>
                            </thead>
                            <tbody>
                            {admins.map((ad) => (
                                <tr key={ad.userId}>
                                    <th scope="row">{ad.userId}</th>
                                    <td>{ad.firstName}</td>
                                    <td>{ad.lastName}</td>
                                    <td>
                                        <Link className="btn btn-primary"
                                              to={`/chat/${userId}/${ad.userId}`}>Chat</Link>
                                    </td>
                                </tr>
                            ))}

                            </tbody>
                        </table>
                    )}
                </div>
            </div>
        </div>
    );
}
