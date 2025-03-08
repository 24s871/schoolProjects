import React, {useEffect, useState} from 'react';
import axios from "axios";
import {Link, useNavigate, useParams} from "react-router-dom";
import Navbar from "../layout/Navbar";

export default function EditDevice() {


    const deviceApiUrl = process.env.REACT_APP_DEVICE_API_URL;

    let navigate = useNavigate();
    const {id}= useParams();
    const device_id = Number(id);

    const [device,setDevice]=useState(
        {
            description:"",
            address:"",
            mhec:"",
            user_id:"",
        }
    )
    const {homeID}= useParams();
    const ret = Number(homeID);

    const{description,address,mhec,user_id}=device

    const onInputChange=(e)=>{

        setDevice({...device, [e.target.name]:e.target.value})
    };

    useEffect(() => {
        void loadDevice();
    },[]);

    const onSubmit=async (e)=>{
        e.preventDefault();
        if (!description || !address || !mhec || !user_id  || isNaN(mhec) || isNaN(user_id)) {
            console.error("All fields must be filled correctly.");
            return;
        }
        try {
            //http://localhost:8081
            //http://localhost/dispozitiveapp
            await axios.put(`http://localhost/dispozitiveapp/device/${device_id}`, device);
            navigate(`/home/${ret}`);
        } catch (error) {
            console.error("Error while updating user:", error);
        }
    };

    const loadDevice = async ()=>{
        if (!device_id || isNaN(device_id)) {
            console.error("Invalid device_id:", device_id);
            return;
        }
        //http://localhost:8081
        //http://localhost/dispozitiveapp
        const result=await axios.get(`http://localhost/dispozitiveapp/device/${device_id}`);
        setDevice(result.data);
    };


    return (
        <body className="app">
        <div className="page">
            <Navbar/>
            <div className="container">
                <div className="row">
                    <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
                        <h2 className="text-center m-4">Edit device</h2>
                        <form onSubmit={(e)=>onSubmit(e)}>
                            <div className="mb-3">
                                <label htmlFor="description" className="form-label">Description:</label>
                                <input type={"text"} className="form-control" placeholder="Enter your description"
                                       name="description" value={description}
                                       onChange={(e)=>onInputChange(e)}/>
                            </div>
                            <div className="mb-3">
                                <label htmlFor="address" className="form-label">Address:</label>
                                <input type={"text"} className="form-control" placeholder="Enter your address"
                                       name="address" value={address}
                                       onChange={(e)=>onInputChange(e)}/>
                            </div>
                            <div className="mb-3">
                                <label htmlFor="mhec" className="form-label">Maximum hourly energy consumption:</label>
                                <input type={"text"} className="form-control" placeholder="Enter your mhec"
                                       name="mhec" value={mhec}
                                       onChange={(e)=>onInputChange(e)}/>
                            </div>
                            <div className="mb-3">
                                <label htmlFor="user_id" className="form-label">User id:</label>
                                <input type={"text"} className="form-control" placeholder="Enter the user id"
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