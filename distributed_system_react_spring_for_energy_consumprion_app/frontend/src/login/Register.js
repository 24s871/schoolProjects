import React, { useState } from 'react';
import axios from 'axios';
import { Link, useNavigate } from "react-router-dom";

export default function Register() {
    const navigate = useNavigate();

    const [register, setRegister] = useState({
        firstName: "",
        lastName: "",
        age: "",
        username: "",
        password: "",
    });

    const { firstName, lastName, age, username, password } = register;

    const onInputChange = (e) => {
        setRegister({ ...register, [e.target.name]: e.target.value });
    };

    const onSubmit = async (e) => {
        e.preventDefault();
        try {
            //http://localhost:8083
            await axios.post(`http://localhost/utilizatorapp/api/register/secure`, register);
            navigate("/");
        } catch (error) {
            console.error("Registration error:", error.response ? error.response.data : error.message);
            alert("Registration failed. Please try again.");
        }
    };

    return (
        <div className="app">
            <div className="page">
                <div className="container">
                    <div className="row">
                        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
                            <h2 className="text-center m-4">Register User</h2>
                            <form onSubmit={onSubmit}>
                                <div className="mb-3">
                                    <label htmlFor="first_name" className="form-label">First Name:</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        placeholder="Enter your first name"
                                        name="firstName"
                                        value={firstName}
                                        onChange={onInputChange}
                                    />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="last_name" className="form-label">Last Name:</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        placeholder="Enter your last name"
                                        name="lastName"
                                        value={lastName}
                                        onChange={onInputChange}
                                    />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="age" className="form-label">Age:</label>
                                    <input
                                        type="number"
                                        className="form-control"
                                        placeholder="Enter your age"
                                        name="age"
                                        value={age}
                                        onChange={onInputChange}
                                    />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="username" className="form-label">Username:</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        placeholder="Enter your username"
                                        name="username"
                                        value={username}
                                        onChange={onInputChange}
                                    />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="password" className="form-label">Password:</label>
                                    <input
                                        type="password"
                                        className="form-control"
                                        placeholder="Enter your password"
                                        name="password"
                                        value={password}
                                        onChange={onInputChange}
                                    />
                                </div>
                                <button type="submit" className="btn btn-primary">Submit</button>
                                <Link className="btn btn-danger mx-2" to="/">Cancel</Link>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
