import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const Success = () => {
    const navigate = useNavigate();

    useEffect(() => {
        const url = new URL(window.location.href);
        const token = url.searchParams.get('token');
        // Change to redux
        if (token) {
            localStorage.setItem('token', token);
            localStorage.setItem('userId', url.searchParams.get('userId'));
            console.log("User ID: ", url.searchParams.get('userId'));
            navigate('/');
        }
    }, [navigate]);

    return null;
}

export default Success;