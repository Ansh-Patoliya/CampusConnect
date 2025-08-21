/**
 * Data Access Object (DAO) package for database operations in CampusConnect.
 * 
 * <p>This package contains all classes responsible for interacting with the PostgreSQL 
 * database. Each DAO class encapsulates SQL operations for a specific entity or 
 * functional area.</p>
 * 
 * &lt;h3&gt;Key DAO Classes:&lt;/h3&gt;
 * <ul>
 *   <li>{@link com.eventApp.DAO.UserDAO} - User authentication and profile management</li>
 *   <li>{@link com.eventApp.DAO.StudentDAO} - Student-specific database operations</li>
 *   <li>{@link com.eventApp.DAO.AdminDAO} - Administrative database operations</li>
 *   <li>{@link com.eventApp.DAO.ClubDAO} - Club management and queries</li>
 *   <li>{@link com.eventApp.DAO.EventDAO} - Event creation, modification, and retrieval</li>
 *   <li>{@link com.eventApp.DAO.ClubMemberDAO} - Club membership management</li>
 *   <li>{@link com.eventApp.DAO.EventRegistrationDAO} - Event registration and participation</li>
 *   <li>{@link com.eventApp.DAO.NotificationDAO} - Notification system operations</li>
 * </ul>
 * 
 * <p>All DAO classes use JDBC for database connectivity and include proper error 
 * handling and resource management.</p>
 * 
 * @since 1.0
 * @author CampusConnect Development Team
 */
package com.eventApp.DAO;