import React, { useState } from 'react';
import axios from 'axios';
import { Link, useNavigate } from "react-router-dom";
import "../stil.css";

// Configure axios defaults
axios.defaults.withCredentials = true;

export default function Login() {
    const navigate = useNavigate();
    const [login, setLogin] = useState({
        username: "",
        password: "",
    });
    const [error, setError] = useState("");

    const { username, password } = login;

    const onInputChange = (e) => {
        setLogin({ ...login, [e.target.name]: e.target.value });
    };

    const onSubmit = async (e) => {
        e.preventDefault();
        setError("");
        try {
            //http://localhost:8083
            const response = await axios.post("http://localhost/utilizatorapp/api/login/secure", login, {
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            const { role, user_id, token } = response.data;

            if (role && user_id && token) {
                localStorage.setItem('token', token);
                if (role === "ADMIN") {
                    navigate(`/home/${user_id}`);
                } else if (role === "CLIENT") {
                    navigate(`/client/${user_id}`);
                } else {
                    setError("Unknown user role");
                }
            } else {
                setError("Invalid response from server");
            }
        } catch (error) {
            console.error("Login error:", error);
            setError(error.response?.data?.message || "Login failed. Please check your credentials.");
        }
    };

    return (
        <div className="app">
            <div className="page">
                <div className="container">
                    <div className="row">
                        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
                            <h2 className="text-center m-4">Login</h2>
                            {error && <div className="alert alert-danger">{error}</div>}
                            <form onSubmit={onSubmit}>
                                <div className="mb-3">
                                    <label htmlFor="username" className="form-label">Username:</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        id="username"
                                        placeholder="Enter your username"
                                        name="username"
                                        value={username}
                                        onChange={onInputChange}
                                        required
                                    />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="password" className="form-label">Password:</label>
                                    <input
                                        type="password"
                                        className="form-control"
                                        id="password"
                                        placeholder="Enter your password"
                                        name="password"
                                        value={password}
                                        onChange={onInputChange}
                                        required
                                    />
                                </div>
                                <button type="submit" className="btn btn-primary">Sign in</button>
                                <Link className="btn btn-danger mx-2" to="/register">
                                    Register
                                </Link>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
