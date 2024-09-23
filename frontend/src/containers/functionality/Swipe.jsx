import React, { useState, useEffect } from 'react';
import { Navbar } from '../../components'
import './swipe.css'

import { motion, useMotionValue, useTransform, AnimatePresence } from 'framer-motion'

import yes from '../../assets/yes-button.png'
import no from '../../assets/no-button.png'
import prev from '../../assets/prev-button.png'

const Swipe = () => {
    // State to hole the list of pets and the current pet index
    const [pets, setPets] = useState([]);
    const [currentIndex, setCurrentIndex] = useState(0);
    const [allPetsViewed, setAllPetsViewed] = useState(false); // New state to track if all pets are viewed

    // Motion value for the x-axis of the card
    const x = useMotionValue(0);
    const y = useMotionValue(0);

    // Transform the x-axis value to rotate the card to create the swipe effect
    const rotate = useTransform(x, [-1000, 0, 1000], [-45, 0, 45]);

    // Fetch the data from the API
    useEffect(() => {
        const fetchPets = async () => {
            try {
                const response = await fetch('http://petinder.bao2803.co:8080/user/pet/recommend?userId=' + localStorage.getItem('userId'));
                const responseJson = await response.json();
                setPets(responseJson.data.pets);
            } catch (error) {
                console.log('ERROR: ', error);
            }
        };
        fetchPets();
    }, []);

    const handleYesClick = async () => {
        if (allPetsViewed) return; // Prevent out-of-bounds access if no pets left
    
        const petId = pets[currentIndex].id;
        
        await fetch(
            `http://petinder.bao2803.co:8080/user/pet/like?userId=` + localStorage.getItem('userId'),
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    petId: petId,
                }),
            }
        );
    
        goToNextPet();
    };
    
    const handleNoClick = async () => {
        if (allPetsViewed) return; // Prevent out-of-bounds access if no pets left
    
        const petId = pets[currentIndex].id;
        
        await fetch(
            `http://petinder.bao2803.co:8080/user/pet/dislike?userId=` + localStorage.getItem('userId'),
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    petId: petId,
                }),
            }
        );
    
        goToNextPet();
    };
    
    const goToNextPet = () => {
        if (currentIndex + 1 < pets.length) {
            setCurrentIndex((prevIndex) => prevIndex + 1);
        } else {
            // If there are no more pets, set the flag to indicate all pets have been viewed
            setAllPetsViewed(true);
        }
    };
    
    const handlePrevClick = () => {
        if (currentIndex > 0) {
            setCurrentIndex((prevIndex) => (prevIndex - 1));
        }
    };

    // Handle swipe end event
    const handleDragEnd = (event, info) => {
        const swipeThreshold = 200; // Minimum distance to consider a swipe
        if (info.offset.x > swipeThreshold) {
            handleYesClick(); // Swipe right, consider it a 'Yes'
        } else if (info.offset.x < -swipeThreshold) {
            handleNoClick(); // Swipe left, consider it a 'No'
        }
        x.set(0); // Reset position after swipe
        y.set(0); // Reset position after swipe
    };

    console.log(pets[currentIndex]);
    return (
        <div className='swipe'>
            <Navbar />
            <div className='swipe__container'>
                {allPetsViewed || pets.length == 0 ? (
                    <div className='swipe__subcontainer' style={{ backgroundColor: 'transparent', boxShadow: 'none' }}>
                        <p className='hubballi-regular out-of-order-msg'>Seems like you have met all of our furry friends!</p>
                    </div>
                ) : (
                    pets.length > 0 && currentIndex < pets.length && (
                        <AnimatePresence>
                            <motion.div 
                                key={pets[currentIndex].id}
                                className='swipe__card' 
                                style={{ x, rotate }}
                                drag='x'
                                dragConstraints={{ left: 0, right: 0 }}
                                onDragEnd={handleDragEnd}
                                initial={{ opacity: 0 }}
                                animate={{ opacity: 1 }}
                                transition={{ duration: 0.3 }}
                            >
                                {/* Card content */}
                                <div className='swipe__subcontainer'>
                                    <div className='swipe__subcontainer-left'>
                                        <div className='swipe__subcontainer-image-container'>
                                            <img src={pets[currentIndex].picture} alt='shelter-dog' className='swipe__subcontainer-image'></img>
                                        </div>
                                    </div>          
                                    <div className='swipe__subcontainer-right'>
                                        <div className='swipe__subcontainer-description-container'>
                                            <h1 className='montserrat-bold'>{pets[currentIndex].name}</h1>
                                            <div className='swipe__subcontainer-description'>
                                                <div className='swipe__subcontainer-description-paragraph'>
                                                    <p className='hubballi-regular'>{pets[currentIndex].description}</p>
                                                </div>
                                                <div className='swipe__subcontainer-description-info hubballi-regular'>
                                                    <p>Eyes colour: {pets[currentIndex].eyeColor}</p>
                                                    <p>Furs colour: {pets[currentIndex].furColor}</p>
                                                    <p>Breed: {pets[currentIndex].breed}</p>
                                                    <p>Age: {pets[currentIndex].age}</p>
                                                    <p>Weight: {pets[currentIndex].weight}</p>
                                                </div>
                                            </div>
                                            <div className='swipe__subcontainer-buttons'>
                                                <div className='swipe__subcontainer-button swipe__subcontainer-buttons-no-image' onClick={handleNoClick}>
                                                    <img src={no} alt='no-button'></img>
                                                </div>
                                                <div className='swipe__subcontainer-button swipe__subcontainer-buttons-prev-image' onClick={handlePrevClick}>
                                                    <img src={prev} alt='prev-button'></img>
                                                </div>
                                                <div className='swipe__subcontainer-button swipe__subcontainer-buttons-yes-image' onClick={handleYesClick}>
                                                    <img src={yes} alt='yes-button'></img>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </motion.div>
                        </AnimatePresence>
                    )
                )}
            </div>
        </div>
    );
}

export default Swipe; 