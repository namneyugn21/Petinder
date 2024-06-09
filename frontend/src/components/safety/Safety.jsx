import React from 'react'
import '../safety/safety.css'
import { motion } from "framer-motion"

const Safety = () => {
    return (
        <div className='safety' id='safety'>
            <div className='safety__container'>
                <motion.h1 
                    className='safety__title inter__bold'
                    initial={{ opacity: 0 }}
                    whileInView={{ opacity: 1 }}
                    transition={{ duration: 0.6 }}>
                        Petinder Safety Tips
                </motion.h1>
                <div className='safety__content'>
                    <motion.div 
                        className='safety__content__item'
                        initial={{ opacity: 0 }}
                        whileInView={{ opacity: 1 }}
                        transition={{ duration: 0.6 }}>
                            <h2 className='safety__content__item-title inter__bold'>Online Safety</h2>
                            <ul className='ibm-plex-mono-regular'>
                                <li className='safety__content__item__description'><span style={{ fontWeight: 'bold' }}>Avoid Sharing Personal Information: </span>Protect your privacy by refraining from sharing sensitive information, such as your home address or personal contact details, with shelters or other users.</li>
                                <li className='safety__content__item__description'><span style={{ fontWeight: 'bold' }}>Use the Platform: </span>Keep your interactions within the Petinder app to maintain privacy and security.</li>
                            </ul>
                    </motion.div>
                    <motion.div 
                        className='safety__content__item'
                        initial={{ opacity: 0 }}
                        whileInView={{ opacity: 1 }}
                        transition={{ duration: 0.6 }}>
                            <h2 className='safety__content__item-title inter__bold'>Meeting Pets at Shelters</h2>
                            <ul className='ibm-plex-mono-regular'>
                                <li className='safety__content__item__description'><span style={{ fontWeight: 'bold' }}>Visit During Open Hours: </span>Plan your visit to the shelter during their open hours and avoid going alone if possible.</li>
                                <li className='safety__content__item__description'><span style={{ fontWeight: 'bold' }}>Ask Questions: </span>Inquire about the pet's history, behavior, and any special needs to ensure a good match.</li>
                                <li className='safety__content__item__description'><span style={{ fontWeight: 'bold' }}>Respect Shelter Rules: </span>Follow the shelter's guidelines and rules during your visit to ensure a positive experience for everyone.</li>
                            </ul>
                    </motion.div>
                    <motion.div 
                        className='safety__content__item'
                        initial={{ opacity: 0 }}
                        whileInView={{ opacity: 1 }}
                        transition={{ duration: 0.6 }}>
                            <h2 className='safety__content__item-title inter__bold'>Safety and Well-being</h2>
                            <ul className='ibm-plex-mono-regular'>
                                <li className='safety__content__item__description'><span style={{ fontWeight: 'bold' }}>Be Prepared: </span>Bring necessary supplies for your visit, such as a leash, carrier, or treats, to make the interaction easier.</li>
                                <li className='safety__content__item__description'><span style={{ fontWeight: 'bold' }}>Consider Your Safety: </span>While pets at shelters are typically safe, be cautious and respectful of their space and behavior.</li>
                            </ul>
                    </motion.div>
                    <motion.div 
                        className='safety__content__item'
                        initial={{ opacity: 0 }}
                        whileInView={{ opacity: 1 }}
                        transition={{ duration: 0.6 }}>
                            <h2 className='safety__content__item-title inter__bold'>Help & Support</h2>
                            <ul className='ibm-plex-mono-regular'>
                                <li className='safety__content__item__description'><span style={{ fontWeight: 'bold' }}>Contact the Shelter: </span>If you have questions or need assistance, reach out to the shelter staff for guidance and support.</li>
                                <li className='safety__content__item__description'><span style={{ fontWeight: 'bold' }}>Emergency Assistance: </span>In case of an emergency or if you need immediate help, contact local authorities or animal control.</li>
                            </ul>
                    </motion.div>
                </div>
            </div>
        </div>
    )
}

export default Safety