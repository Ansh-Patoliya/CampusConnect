/**
 * Model package containing entity classes for the CampusConnect system.
 * 
 * <p>This package defines the core business objects and data structures used throughout 
 * the application. Each class represents a fundamental entity in the campus event 
 * management domain.</p>
 * 
 * &lt;h3&gt;Key Classes:&lt;/h3&gt;
 * <ul>
 *   <li>{@link com.eventApp.Model.User} - Base user entity with authentication and profile information</li>
 *   <li>{@link com.eventApp.Model.Student} - Student-specific information including academic details and interests</li>
 *   <li>{@link com.eventApp.Model.Admin} - Administrative user with system management privileges</li>
 *   <li>{@link com.eventApp.Model.Club} - Club entity with membership and organizational information</li>
 *   <li>{@link com.eventApp.Model.Event} - Event entity containing all event-related data</li>
 *   <li>{@link com.eventApp.Model.ClubMember} - Association between users and clubs</li>
 *   <li>{@link com.eventApp.Model.Notification} - System notification entity</li>
 * </ul>
 * 
 * <p>All model classes follow JavaBean conventions with appropriate getters, setters, 
 * and constructors for various use cases.</p>
 * 
 * @since 1.0
 * @author CampusConnect Development Team
 */
package com.eventApp.Model;