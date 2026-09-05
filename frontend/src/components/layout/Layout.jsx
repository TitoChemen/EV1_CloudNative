import React from 'react';
import Navbar from './Navbar';
import Footer from './Footer';
import '../../styles/Layout.css';

export default function Layout({ children }) {
  return (
    <div className="layout-wrapper">
      <Navbar />
      <main className="layout-content">
        {children}
      </main>
      <Footer />
    </div>
  );
}