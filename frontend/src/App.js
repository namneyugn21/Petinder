import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './containers/home/Home';
import { Navbar } from './components';
import { GoogleOAuthProvider } from '@react-oauth/google';
import './App.css';

const App = () => {
    return (
        <div className='App'>
            <GoogleOAuthProvider clientId="739734105596-e3nblstcv5sil9chb25uo00q8g0ettdj.apps.googleusercontent.com">
                <Router>
                    <Navbar />
                    <Routes>
                        <Route path='/' element={<Home />} />
                    </Routes>
                </Router>
            </GoogleOAuthProvider>
        </div>
    ) 
}

export default App;