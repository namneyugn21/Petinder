import React, { useState } from 'react';
import { RiMenu3Line, RiCloseLine } from 'react-icons/ri';
import { PiPawPrintFill } from "react-icons/pi";
import { NavLink } from 'react-router-dom';
import './navbar.css';
import logo from '../../assets/logo.png';
import Login from '../login/Login';

const Menu = () => (
    <>
        <div className='navbar__menu-item'>
            <a href='#about'>About</a>
            <PiPawPrintFill size={15} className='fade-in' />
        </div>
        <div className='navbar__menu-item'>
            <a href='#discover'>Discover</a>
            <PiPawPrintFill size={15} className='fade-in' />
        </div>
        <div className='navbar__menu-item'>
            <a href='#safety'>Safety</a>
            <PiPawPrintFill size={15} className='fade-in' />
        </div>  
    </>
);

const Navbar = () => {
    const [isToggleMenuVisible, setisToggleMenuVisible] = useState(false);
    const [isLoginFormVisible, setIsLoginFormVisible] = useState(false);
    const [isFadingOut, setIsFadingOut] = useState(false);
    const [isToggleButtonVisible, setIsToggleButtonVisible] = useState(false);

    const toggleLoginForm = () => {
        if (isLoginFormVisible) {
            setIsFadingOut(true);
            setTimeout(() => {
                setIsLoginFormVisible(false);
                setIsFadingOut(false);
            }, 250);
        } else {
            setIsLoginFormVisible(true);
        }
    };

    const toggleMenu = () => {
        if (isToggleMenuVisible) {
            setIsFadingOut(true);
            setIsToggleButtonVisible(false);
            setTimeout(() => {
                setisToggleMenuVisible(false);
                setIsFadingOut(false);
            }, 250);
        } else {
            setisToggleMenuVisible(true);
            setIsToggleButtonVisible(true);
        }
    };

    return (
        <div className='navbar'>
            <NavLink to='/'>
                <div className='navbar__title inter__bold'>
                    <div className='navbar__logo'>
                        <img src={logo} alt='logo' />
                    </div>
                    <div>
                        Petinder
                    </div>                
                </div>
            </NavLink>
            <div className='navbar__menu ibm-plex-mono-regular'>
                <Menu />
                <div className='navbar__menu-item'>
                    <button className='navbar__menu-button ibm-plex-mono-regular' type='button' onClick={toggleLoginForm}>
                        Sign In
                    </button>
                </div>
            </div>

            {/* Adding sign in form */}
            <Login isLoginFormVisible={isLoginFormVisible} toggleLoginForm={toggleLoginForm} isFadingOut={isFadingOut} />

            {/* Adding mobile menu toggle */}
            {<div className='navbar__menu-toggle'>
                {
                    isToggleMenuVisible 
                        ? <RiCloseLine className={isToggleButtonVisible ? 'fade-in-button' : 'fade-out-button'} color = '#000' size={25} onClick={() => toggleMenu()} />
                        : <RiMenu3Line className={isToggleButtonVisible ? 'fade-out-button' : 'fade-in-button'} color = '#000' size={25} onClick={() => toggleMenu()} />
                }
                { // Adding mobile menu dropdown
                    isToggleMenuVisible && (
                    <div className={`navbar__menu-dropdown ${isFadingOut ? 'fade-out' : 'fade-in'} ibm-plex-mono-regular`}>
                        <div className='navbar__menu-item tracking-in-expand' onClick={() => toggleMenu()}><a href='#about'>About</a></div>
                        <div className='navbar__menu-item tracking-in-expand' onClick={() => toggleMenu()}><a href='#discover'>Discover</a></div>
                        <div className='navbar__menu-item tracking-in-expand' onClick={() => toggleMenu()}><a href='#safety'>Safety</a></div>  
                        <div className='navbar__menu-item tracking-in-expand'>
                            <button className='navbar__menu-button ibm-plex-mono-regular tracking-in-expand' type='button' onClick={() => {toggleLoginForm(); setisToggleMenuVisible(false);}}>Sign In</button>
                        </div>  
                    </div>
                    )
                }
            </div>}

            {isToggleMenuVisible && <div className="navbar__overlay fade-in" onClick={() => toggleMenu()}></div>}
        </div>
    )
}

export default Navbar;