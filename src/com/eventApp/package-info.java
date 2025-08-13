/**
 * CampusConnect - Campus Event Management System
 * 
 * <p>This package contains the core components of the CampusConnect application,
 * a comprehensive campus event management system built with JavaFX and PostgreSQL.
 * The system facilitates seamless interaction between students, club members,
 * and administrators for organizing and participating in campus activities.</p>
 * 
 * <h2>Package Structure</h2>
 * <ul>
 * <li><strong>Controller</strong> - JavaFX controllers implementing the MVC pattern</li>
 * <li><strong>DAO</strong> - Data Access Objects for database operations</li>
 * <li><strong>DataStructures</strong> - Custom data structures for efficient operations</li>
 * <li><strong>ExceptionHandler</strong> - Custom exception classes for error handling</li>
 * <li><strong>FXML</strong> - JavaFX user interface layout files</li>
 * <li><strong>Loader</strong> - Utility classes for loading resources and screens</li>
 * <li><strong>Model</strong> - Entity classes representing business objects</li>
 * <li><strong>Service</strong> - Business logic layer services</li>
 * <li><strong>Utils</strong> - Utility classes for common operations</li>
 * </ul>
 * 
 * <h2>Key Features</h2>
 * <ul>
 * <li><strong>Multi-role System</strong> - Supports Students, Club Members, and Administrators</li>
 * <li><strong>Event Management</strong> - Complete event lifecycle from creation to completion</li>
 * <li><strong>Club Management</strong> - Club creation, membership, and activity tracking</li>
 * <li><strong>Approval Workflows</strong> - Administrative approval for clubs and events</li>
 * <li><strong>Personalization</strong> - Interest-based event recommendations for students</li>
 * <li><strong>Payment Integration</strong> - Support for paid events and registration fees</li>
 * <li><strong>Data Analytics</strong> - Reporting and analytics for administrators</li>
 * </ul>
 * 
 * <h2>Architecture</h2>
 * <p>The application follows the Model-View-Controller (MVC) architectural pattern:</p>
 * <ul>
 * <li><strong>Model</strong> - Business entities (User, Event, Club, etc.)</li>
 * <li><strong>View</strong> - JavaFX FXML files for user interfaces</li>
 * <li><strong>Controller</strong> - JavaFX controllers managing UI interactions</li>
 * <li><strong>Service</strong> - Business logic layer</li>
 * <li><strong>DAO</strong> - Data access layer</li>
 * </ul>
 * 
 * <h2>Usage</h2>
 * <p>To start the application, run the {@link com.eventApp.Main} class which
 * initializes the JavaFX application and displays the login screen.</p>
 * 
 * <h2>Dependencies</h2>
 * <ul>
 * <li>JavaFX - Desktop UI framework</li>
 * <li>PostgreSQL JDBC Driver - Database connectivity</li>
 * <li>Java 11+ - Runtime environment</li>
 * </ul>
 * 
 * @author CampusConnect Development Team
 * @version 1.0
 * @since 2024
 */
package com.eventApp;