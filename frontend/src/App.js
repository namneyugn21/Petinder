import React from 'react';

import { Header, Footer, Body } from './containers';
import { Navbar } from './components';
import './App.css';

const App = () => {
    return (
        <div className='App'>
            <Navbar />
            <Header />
        </div>
    ) 
}

export default App