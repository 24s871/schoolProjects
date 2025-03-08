import React, { useEffect, useState } from 'react';
import axios from "axios";
import { Link, useNavigate, useParams } from "react-router-dom";
import Navbar from "../layout/Navbar";

export default function EditUser() {
    const userApiUrl = process.env.REACT_APP_USER_API_URL || 'http://localhost:8080/api';
    const navigate = useNavigate();
    const { id, homeID } = useParams();
    const userId = Number(id);
    const returnId = Number(homeID);

    const [user, setUser] = useState({
        firstName: "",
        lastName: "",
        age: "",
        username: "",
        password: "",
        role: ""
    });

    const { firstName, lastName, age, username, password, role } = user;

    useEffect(() => {
        loadUser();
    }, []);

    const onInputChange = (e) => {
        setUser({ ...user, [e.target.name]: e.target.value });
    };

    const onSubmit = async (e) => {
        e.preventDefault();
        if (!firstName || !lastName || !username || !role || isNaN(age)) {
            alert("All fields must be filled correctly.");
            return;
        }
        try {
            const updatedUser = {
                firstName,
                lastName,
                age,
                username,
                password,
                role
            };
           // console.log("Sending PUT request to:", `http://localhost:8080/api/user/${userId}`);
            console.log("Request data:", JSON.stringify(updatedUser, null, 2));
            //http://localhost:8083
            const response = await axios.put(`http://localhost/utilizatorapp/user/${userId}`, updatedUser);

            console.log("Response received:", response.data);
            alert("User updated successfully");
            navigate(`/home/${returnId}`);
        } catch (error) {
            console.error("Error while updating user:", error);
            if (error.response) {
                console.error("Response data:", error.response.data);
                console.error("Response status:", error.response.status);
                console.error("Response headers:", error.response.headers);
                switch (error.response.status) {
                    case 400:
                        alert("Bad request. Please check your input data.");
                        break;
                    case 404:
                        alert("User not found. Unable to update.");
                        break;
                    default:
                        alert(`An error occurred while updating the user. Status: ${error.response.status}`);
                }
            } else if (error.request) {
                console.error("No response received:", error.request);
                alert("No response received from the server. Please check your internet connection.");
            } else {
                console.error("Error setting up request:", error.message);
                alert("An error occurred while setting up the request.");
            }
        }
    };

    const loadUser = async () => {
        if (!userId || isNaN(userId)) {
            console.error("Invalid userId:", userId);
            navigate(`/home/${returnId}`);
            return;
        }
        try {
            console.log("Sending GET request to:", `${userApiUrl}/user/${userId}`);

            const result = await axios.get(`http://localhost/utilizatorapp/user/${userId}`);

            console.log("User data received:", result.data);
            setUser(result.data);
        } catch (error) {
            console.error("Error loading user:", error);
            if (error.response && error.response.status === 404) {
                alert("User not found. Redirecting to home page.");
            } else {
                alert("An error occurred while loading the user data.");
            }
            navigate(`/home/${returnId}`);
        }
    };

    return (
        <div className="app">
            <div className="page">
                <Navbar />
                <div className="container">
                    <div className="row">
                        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
                            <h2 className="text-center m-4">Edit User</h2>
                            <form onSubmit={onSubmit}>
                                {[
                                    { name: 'firstName', label: 'First Name' },
                                    { name: 'lastName', label: 'Last Name' },
                                    { name: 'age', label: 'Age', type: 'number' },
                                    { name: 'username', label: 'Username' },
                                    { name: 'password', label: 'Password', type: 'password' },
                                    { name: 'role', label: 'Role' }
                                ].map((field) => (
                                    <div className="mb-3" key={field.name}>
                                        <label htmlFor={field.name} className="form-label">
                                            {field.label}:
                                        </label>
                                        <input
                                            type={field.type || 'text'}
                                            className="form-control"
                                            placeholder={`Enter ${field.label.toLowerCase()}`}
                                            name={field.name}
                                            value={user[field.name]}
                                            onChange={onInputChange}
                                        />
                                    </div>
                                ))}
                                <button type="submit" className="btn btn-primary">Submit</button>
                                <Link className="btn btn-danger mx-2" to={`/home/${returnId}`}>Cancel</Link>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
