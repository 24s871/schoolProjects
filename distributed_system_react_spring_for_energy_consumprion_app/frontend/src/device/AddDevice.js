import React, {useState} from 'react';
import axios from "axios";
import {Link, useNavigate, useParams} from "react-router-dom";
import Navbar from "../layout/Navbar";


export default function AddDevice() {


    const deviceApiUrl = process.env.REACT_APP_DEVICE_API_URL;

    let navigate = useNavigate();

    const [device,setDevice]=useState(
        {
            description:"",
            address:"",
            mhec:"",
            user_id:""
        }
    )

    const {homeID}= useParams();
    const ret = Number(homeID);

    const{description,address,mhec,user_id}=device

    const onInputChange=(e)=>{

        setDevice({...device, [e.target.name]:e.target.value})
    };

    const onSubmit=async (e)=>{
        e.preventDefault();
        //http://localhost:8081
        //http://localhost/dispozitiveapp
        await axios.post(`http://localhost/dispozitiveapp/device`,device)
        navigate(`/home/${homeID}`)
    };

    return (
        <body className="app">
        <div className="page">
            <Navbar/>
            <div className="container">
                <div className="row">
                    <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
                        <h2 className="text-center m-4">Add new device</h2>
                        <form onSubmit={(e)=>onSubmit(e)}>
                            <div className="mb-3">
                                <label htmlFor="description" className="form-label">Description:</label>
                                <input type={"text"} className="form-control" placeholder="Enter your device description"
                                       name="description" value={description}
                                       onChange={(e)=>onInputChange(e)}/>
                            </div>
                            <div className="mb-3">
                                <label htmlFor="address" className="form-label">Address:</label>
                                <input type={"text"} className="form-control" placeholder="Enter your device address"
                                       name="address" value={address}
                                       onChange={(e)=>onInputChange(e)}/>
                            </div>
                            <div className="mb-3">
                                <label htmlFor="mhec" className="form-label">Maximum hourly energy consumption:</label>
                                <input type={"text"} className="form-control" placeholder="Enter your device maximum hourly energy consumption"
                                       name="mhec" value={mhec}
                                       onChange={(e)=>onInputChange(e)}/>
                            </div>
                            <div className="mb-3">
                                <label htmlFor="user_id" className="form-label">Id of the user:</label>
                                <input type={"text"} className="form-control" placeholder="Enter your user id"
                                       name="user_id" value={user_id}
                                       onChange={(e)=>onInputChange(e)}/>
                            </div>
                            <button type="submit" className="btn btn-primary">Submit</button>
                            <Link type="submit" className="btn btn-danger mx-2" to={`/home/${ret}`}>Cancel</Link>
                        </form>
                    </div>
                </div>

            </div>
        </div>
        </body>
    )
}