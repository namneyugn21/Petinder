import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './containers/home/Home';
import { Navbar } from './components';
import Login from './containers/login/Login';
import './App.css';

const App = () => {
    return (
        <div className='App'>
            <Router>
                <Navbar />
                <Routes>
                    <Route path='/' element={<Home />} />
                    <Route path='/login' element={<Login />} />
                </Routes>
            </Router>
        </div>
    ) 
}

export default App;