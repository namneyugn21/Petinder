import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const Success = () => {
    const navigate = useNavigate();

    useEffect(() => {
        const fetchData = async () => {
            const url = new URL(window.location.href);
            const token = url.searchParams.get('token');
            if (token) {
                localStorage.setItem('token', token);
                localStorage.setItem('userId', url.searchParams.get('userId'));
                try {
                    const response = await fetch('http://petinder.bao2803.co:8080/user/' + localStorage.getItem('userId'));
                    const responseJson = await response.json();
                    const data = responseJson.data;
                    localStorage.setItem('picture', data.picture);
                } catch (error) {
                    console.log('ERROR: ', error);
                }
                navigate('/');
            }
        };

        fetchData();
    }, [navigate]);

    return null;
};

export default Success;
