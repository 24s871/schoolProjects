import React, {useEffect, useState} from 'react';
import axios from "axios";
import {Link, useParams} from "react-router-dom";
import Navbar from "../layout/Navbar";

export default function ViewUser() {
    const [user, setUser] = useState({
        firstName: "",
        lastName: "",
        age: "",
        username: "",
        password: "",
        role: ""
    });
    const {homeID, id} = useParams();
    const ret = Number(homeID);
    const user_id = Number(id);

    useEffect(() => {
        loadUser();
    }, []);

    const loadUser = async () => {
        try {
            //http://localhost:8083
            const result = await axios.get(`http://localhost/utilizatorapp/user/${user_id}`);
            setUser(result.data);
        } catch (error) {
            console.error("Error loading user:", error);
        }
    };

    return (
        <div className="page app">
            <Navbar/>
            <div className="container">
                <div className="row">
                    <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
                        <h2 className="text-center m-4">User details</h2>
                        <div className="card">
                            <div className="card-header">
                                Details of user id: {user.userId}
                                <ul className="list-group list-group-flush">
                                    <li className="list-group-item"><b>First name:</b> {user.firstName}</li>
                                    <li className="list-group-item"><b>Last name:</b> {user.lastName}</li>
                                    <li className="list-group-item"><b>Age:</b> {user.age}</li>
                                    <li className="list-group-item"><b>Username:</b> {user.username}</li>
                                    <li className="list-group-item"><b>Password:</b> {user.password}</li>
                                    <li className="list-group-item"><b>Role:</b> {user.role}</li>
                                </ul>
                            </div>
                        </div>
                        <Link className="btn btn-primary my-2" to={`/home/${ret}`}>Back to Home</Link>
                    </div>
                </div>
            </div>
        </div>
    );
}
