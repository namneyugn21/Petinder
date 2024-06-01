import React, { useState } from 'react';
import { RiMenu3Line, RiCloseLine } from 'react-icons/ri';
import './navbar.css';

const Menu = () => (
    <>
    <div className='navbar__menu-item'>About</div>
    <div className='navbar__menu-item'>Discover</div>
    <div className='navbar__menu-item'>Learn</div>  
    </>
)

const Navbar = () => {
    const [toggleMenu, setToggleMenu] = useState(false);

    return (
        <div className='navbar'>
            <div className='navbar__title inter__bold'>
                <div className='navbar__logo'>
                    <img src='https://via.placeholder.com/150' alt='logo' />
                </div>
                Petinder
            </div>
            <div className='navbar__menu inter__medium'>
                <Menu />
                <div className='navbar__menu-item'>
                    <button className='navbar__menu-button' type='button'>Sign In</button>
                </div>
            </div>
            {<div className='navbar__menu-toggle'>
                {
                    toggleMenu 
                        ? <RiCloseLine color = '#000' size={25} onClick={() => setToggleMenu(!toggleMenu)} />
                        : <RiMenu3Line color = '#000' size={25} onClick={() => setToggleMenu(!toggleMenu)} />
                }
                { // Adding mobile menu dropdown
                    toggleMenu && (
                        <div className='navbar__menu-dropdown inter__medium'>
                            <div className='navbar__menu-item tracking-in-expand'>About</div>
                            <div className='navbar__menu-item tracking-in-expand'>Discover</div>
                            <div className='navbar__menu-item tracking-in-expand'>Learn</div>  
                            <div className='navbar__menu-item tracking-in-expand'>Sign In</div>  
                        </div> 
                        
                    )
                }
            </div>}
        </div>
    )
}

export default Navbar