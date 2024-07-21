import React, {useState, useEffect } from 'react'
import Navbar from '../../components/navbar/Navbar'
import './user.css'

const User = () => {
    const [firstName, setFirstName] = useState('');
    const [middleName, setMiddleName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const response = await fetch('http://34.134.180.221/user/23c3ce4e-074e-44a5-bb57-956cf025dcf6');
                const reponseJson = await response.json();
                const data = reponseJson.data;
                setFirstName(data.firstName);
                setMiddleName(data.middleName);
                setLastName(data.lastName);
                setEmail(data.email);
            } catch (error) {
                console.log('ERROR: ', error);
            }
        }
        fetchUserData();
    }, [])

    return (
        <>
            <Navbar/>
            <div className='user-container'>
                <div className='user-info-container'>
                    <h1>User Profile</h1>
                    <p>First Name: {firstName}</p>
                    <p>Middle Name: {middleName}</p>
                    <p>Last Name: {lastName}</p>
                    <p>Email: {email}</p>
                </div>
            </div>
        </>

    )
}

export default User