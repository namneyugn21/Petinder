import React, {useState, useEffect } from 'react'
import Navbar from '../../components/navbar/Navbar'
import './User.css'

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
                    <div className='user-info-header'>
                        <img src='https://www.w3schools.com/howto/img_avatar.png' alt='avatar' />
                        <h1 className='ibm-plex-mono-bold'>{firstName} {lastName}</h1>
                    </div>
                    <div className='user-info-fields'>
                        <p className='ibm-plex-mono-regular'><span className='ibm-plex-mono-medium highlighter'>First Name:</span> {firstName}</p>
                        <p className='ibm-plex-mono-regular'><span className='ibm-plex-mono-medium highlighter'>Middle Name:</span> {middleName}</p>                        
                        <p className='ibm-plex-mono-regular'><span className='ibm-plex-mono-medium highlighter'>Last Name:</span> {lastName}</p>
                        <p className='ibm-plex-mono-regular'><span className='ibm-plex-mono-medium highlighter'>Email:</span> {email}</p>                    
                    </div>
                </div>
            </div>
        </>

    )
}

export default User