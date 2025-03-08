import React, { useState } from 'react';
import axios from "axios";
import { Link, useNavigate, useParams } from "react-router-dom";
import Navbar from "../layout/Navbar";

export default function AddUser() {
    const navigate = useNavigate();
    const { homeID } = useParams();
    const ret = Number(homeID);
    const [user, setUser] = useState({
        firstName: "",
        lastName: "",
        age: "",
        username: "",
        password: "",
        role:""
    });

    const { firstName, lastName, age, username, password,role } = user;

    const onInputChange = (e) => {
        setUser({ ...user, [e.target.name]: e.target.value });
    };

    const onSubmit = async (e) => {
        e.preventDefault();
        try { //http://localhost:8083
            const res = await axios.post(`http://localhost/utilizatorapp/add/secure`, user);
            if (res.status === 200) {
                navigate(`/home/${homeID}`);
            }
        } catch (error) {
            if (error.response && error.response.status === 403) {
                alert("Access Denied: Only admins can add new users.");
            } else if (error.response && error.response.data) {
                alert(error.response.data);
            } else {
                alert("An error occurred while registering the user.");
            }
        }
    };

    return (
        <div className="page app">
            <Navbar />
            <div className="container">
                <div className="row">
                    <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
                        <h2 className="text-center m-4">Register User</h2>
                        <form onSubmit={(e) => onSubmit(e)}>
                            <div className="mb-3">
                                <label htmlFor="firstName" className="form-label">First Name:</label>
                                <input type="text" className="form-control" placeholder="Enter your first name"
                                       name="firstName" value={firstName} onChange={(e) => onInputChange(e)}/>
                            </div>
                            <div className="mb-3">
                                <label htmlFor="lastName" className="form-label">Last Name:</label>
                                <input type="text" className="form-control" placeholder="Enter your last name"
                                       name="lastName" value={lastName} onChange={(e) => onInputChange(e)}/>
                            </div>
                            <div className="mb-3">
                                <label htmlFor="age" className="form-label">Age:</label>
                                <input type="number" className="form-control" placeholder="Enter your age"
                                       name="age" value={age} onChange={(e) => onInputChange(e)}/>
                            </div>
                            <div className="mb-3">
                                <label htmlFor="username" className="form-label">Username:</label>
                                <input type="text" className="form-control" placeholder="Enter your username"
                                       name="username" value={username} onChange={(e) => onInputChange(e)}/>
                            </div>
                            <div className="mb-3">
                                <label htmlFor="password" className="form-label">Password:</label>
                                <input type="password" className="form-control" placeholder="Enter your password"
                                       name="password" value={password} onChange={(e) => onInputChange(e)}/>
                            </div>

                            <div className="mb-3">
                                <label htmlFor="role" className="form-label">Role:</label>
                                <input type="text" className="form-control" placeholder="Enter your role"
                                       name="role" value={role} onChange={(e) => onInputChange(e)}/>
                            </div>
                            <button type="submit" className="btn btn-primary">Submit</button>
                            <Link className="btn btn-danger mx-2" to={`/home/${ret}`}>Cancel</Link>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
}
