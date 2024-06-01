import React from 'react'
import './navbar.css'

const Navbar = () => {
  return (
    <div className='navbar'>
        <div className='navbar-title inter-bold'>Petinder</div>
        <div className='navbar-menu inter-medium'>
            <div className='navbar-menu-item'>About</div>
            <div className='navbar-menu-item'>Discover</div>
            <div className='navbar-menu-item'>Learn</div>
            <div className='navbar-menu-item'>Sign In</div>
        </div>
    </div>
  )
}

export default Navbar