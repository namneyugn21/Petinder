import React, { useState, useEffect } from 'react';
import { Navbar } from '../../components'
import './swipe.css'
import demo from '../../assets/demo.webp'

const Swipe = () => {
    const [pets, setPets] = useState({
        pets: [],
        nextPage: 0, 
        nextSize: 10, 
        totalPage: 1
    });

    useEffect(() => {
        const fetchPets = async () => {
            try {
                const response = await fetch('http://petinder.bao2803.co/pet?page=0&size=10&sort=name%2CASC');
                const responseJson = await response.json();
                const data = responseJson.data;
                setPets(data);
            } catch (error) {
                console.log('ERROR: ', error);
            }
        };
        fetchPets();
    }, []);

    console.log(pets.pets.length);
    console.log(pets.pets[0].name);
    
    return (
        <div className='swipe'>
            <Navbar />
            <div className='swipe__container'>
                <div className='swipe__subcontainer-left'>
                    <div className='swipe__subcontainer-description-container'>
                        <h1 className='inter__bold'>Cookies</h1>
                        <div className='swipe__subcontainer-description'> 
                            <div className='swipe__subcontainer-description-paragraph'>
                                <p className='hubballi-regular'>
                                    Hi there!
                                    My name is Cookies, and I'm a playful Golden Retriever. I'm 3 years old and full of energy and love. I love playing fetch, going on long walks, and getting lots of belly rubs. I can't wait to find my forever home where I can share all my tail wags and joy. Come visit me at the shelter and let's be best friends!
                                </p>
                            </div>
                            <div className='swipe__subcontainer-description-info hubballi-regular'>
                                <p>Eyes colour: black</p>
                                <p>Furs colour: brown</p>
                                <p>Breed: golden retriever</p>
                                <p>Age: 3</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div className='swipe__subcontainer-right'>
                    <div className='swipe__subcontainer-image-container'>
                        <img src={demo} alt='shelter-dog' className='swipe__subcontainer-image'></img>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Swipe; 