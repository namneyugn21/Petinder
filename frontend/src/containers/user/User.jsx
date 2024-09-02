import React, {useState, useEffect } from 'react'
import Navbar from '../../components/navbar/Navbar'
import './User.css'

const User = () => {
    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const response = await fetch('http://petinder.bao2803.co/user/6c49de5a-5288-4dea-871e-8eed5dd3989f');
                const responseJson = await response.json();
                console.log(responseJson);
                localStorage.setItem('firstName', responseJson.data.firstName);
                localStorage.setItem('lastName', responseJson.data.lastName);
                localStorage.setItem('email', responseJson.data.email);
                localStorage.setItem('picture', responseJson.data.picture);
                localStorage.setItem('description', responseJson.data.description);
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
                    <div className='user-info'>
                        <div className='user-info__avatar'>
                            <img src={localStorage.getItem('picture')} alt='avatar' />
                        </div>
                        <div className='user-info__information'>
                            <h1 className='montserrat-semibold'>{localStorage.getItem('firstName')} {localStorage.getItem('lastName')}</h1>
                            <p className='montserrat-medium'>{localStorage.getItem('email')}</p>
                        </div>
                        <div className='user-info__description-container'>
                            <p className='user-info__description montserrat-light'>{localStorage.getItem('description')}</p>
                        </div>
                        <div className='user-info__edit-button-container'>
                            <button className='user-info__edit-button montserrat-medium'>Edit</button>
                        </div>
                    </div>
                </div>
            </div>
        </>

    )
}

export default User