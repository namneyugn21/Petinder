import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const Success = () => {
    const navigate = useNavigate();

    useEffect(() => {
        const url = new URL(window.location.href);
        const token = url.searchParams.get('token');
        if (token) {
            localStorage.setItem('token', token);
            navigate('/');
        }
    }, [navigate]);

    return null;
}

export default Success;