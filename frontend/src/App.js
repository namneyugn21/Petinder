import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './containers/home/Home';
import Success from './components/Success';
import User from './containers/user/User';
import Swipe from './containers/functionality/Swipe';
import './App.css';

const App = () => {
    return (
        <div className='App'>
            <Router>
                <Routes>
                    <Route path='/' element={<Home />} />
                    <Route path='/success' element={<Success />} />
                    <Route path='/user' element={<User />} />
                    <Route path='/swipe' element={<Swipe />} />
                </Routes>
            </Router>
        </div>
    )
}

export default App;