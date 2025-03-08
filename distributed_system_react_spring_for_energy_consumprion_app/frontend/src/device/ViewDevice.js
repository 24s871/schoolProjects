import React, {useEffect, useState} from 'react';
import axios from "axios";
import {Link, useParams} from "react-router-dom";
import Navbar from "../layout/Navbar";

export default function ViewDevice() {


    const deviceApiUrl = process.env.REACT_APP_DEVICE_API_URL;

    const [device, setDevice] = useState(
        {
            description:"",
            address:"",
            mhec:"",
            user_id:"",
        }
    );

    const {id}= useParams();
    const device_id = Number(id);
    const {homeID}= useParams();
    const ret = Number(homeID);

    useEffect(()=>{
        void loadDevice();
    },[]);

    const loadDevice=async ()=>{
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
                        <h2 className="text-center m-4">Device details</h2>
                        <div className="card">

                            <div className="card-header">
                                Details of user id:{device.device_id}
                                <ul className="list-group list-group-flush">
                                    <li className="list-group-item"><b>Description:</b>
                                        {device.description}</li>
                                    <li className="list-group-item"><b>Address:</b>
                                        {device.address}</li>
                                    <li className="list-group-item"><b>Maximum hourly energy consumption:</b>
                                        {device.mhec}</li>
                                    <li className="list-group-item"><b>User id:</b>
                                        {device.user_id}</li>
                                </ul>
                            </div>

                        </div>
                        <Link className="btn btn-primary my-2" to={`/home/${ret}`}>Back to Home</Link>
                    </div>
                </div>

            </div>
        </div>
        </body>
    )

}