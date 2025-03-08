import React, {useEffect, useState} from "react";
import axios from "axios";
import {useParams} from "react-router-dom";
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from "recharts";




export default function Chart() {

    const [devices, setDevices]=useState([]);
    const [date, setDate] = useState("");

    const {id}= useParams();
    const user_id = Number(id);
    const [loading, setLoading] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");

    const loadDevices = async () => {
        if (!date) return;

        setLoading(true);
        setErrorMessage("");
        try {//http://localhost:8082
            //http://localhost/monitorapp/${user_id}
            const result = await axios.get(`http://localhost/monitorapp/${user_id}`, {
                params: { date },
            });

            if (Object.keys(result.data).length === 0) {
                setErrorMessage("Niciun consum disponibil pentru această dată.");
                setDevices({});
            } else {
                setDevices(result.data);
            }
        } catch (error) {
            setErrorMessage("Eroare la încărcarea datelor.");
            console.error("Error loading devices:", error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (date) {
            loadDevices();
        }
    }, [date]);

    return (
        <div className="app">
            <h2>Energy Consumption</h2>

            {/* Calendar for date selection */}
            <input
                type="date"
                value={date}
                onChange={(e) => setDate(e.target.value)}
                style={{ marginBottom: "20px" }}
            />

            {loading && <p>Se încarcă datele...</p>}
            {errorMessage && <p>{errorMessage}</p>}

            {!loading &&
                !errorMessage &&
                Object.keys(devices).length === 0 && <p>Niciun consum disponibil pentru această dată.</p>}

            {/* Display charts for each device */}
            {!loading &&
                !errorMessage &&
                Object.entries(devices).map(([deviceId, tracks]) => (
                    <div key={deviceId} style={{
                        marginBottom: "50px",
                        padding: "20px",
                        backgroundColor: "#f5f5dc", // bej
                        borderRadius: "10px",
                    }}>
                        <h3>Device ID: {deviceId}</h3>
                        {(tracks.length === 0 || false)? (
                            <p>Device ID: {deviceId} nu este consum.</p>
                        ) : (
                            <LineChart
                                width={800}
                                height={400}
                                data={tracks.map((track) => ({
                                    moment: new Date(track.moment).toLocaleTimeString(),
                                    consumption: track.consumption,
                                }))}
                                margin={{ top: 20, right: 30, left: 20, bottom: 5 }}
                            >
                                <CartesianGrid strokeDasharray="3 3" />
                                <XAxis dataKey="moment" />
                                <YAxis />
                                <Tooltip />
                                <Legend />
                                <Line type="monotone" dataKey="consumption" stroke="#8884d8" />
                            </LineChart>
                        )}
                    </div>
                ))}
        </div>
    );
}

