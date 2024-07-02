import React from 'react'
import './home.css'
import Safety from '../../components/safety/Safety'
import About from '../../components/about/About'
import { Navbar } from '../../components'

const Home = () => {
    return (
        <>
            <Navbar/>
            <About/>
            <Safety />
        </>
    )
}

export default Home