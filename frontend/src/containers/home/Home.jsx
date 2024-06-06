import React from 'react'
import { NavLink } from 'react-router-dom'
import './home.css'
import index from '../../assets/index.jpeg'

const Home = () => {
    return (
        <div className='about' id='about'>
            <div className='about__title'>
                <h1 className='inter__bold slide-top-title'>Find your sidekick !</h1>
                <div className='about__introduction ibm-plex-mono-regular slide-top-introduction'>
                    <p>Welcome to Petinder,<br></br><br></br>You can now find your perfect furry companion is as easy as swiping right! Our web application connects you with adorable pets available for adoption at local shelters.<br></br><br></br> Discover your new best friend today!</p>
                </div>
                <div className='about__button slide-top-button'>
                    <button className='ibm-plex-mono-regular'>Get Started</button>
                </div>
            </div>
            <div className='about__image'>
                <img src={index} alt='smiling dog' />
            </div>
        </div>
    )
}

export default Home