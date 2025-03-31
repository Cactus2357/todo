USE todo_db;

TRUNCATE TABLE status;
INSERT INTO status (status_type, status_name, description) VALUES
('user', 'inactive', 'User account is inactive'),
('user', 'active', 'User account is active'),
('user', 'pending', 'User registration is pending approval'),
('user', 'banned', 'User is banned due to violations'),
('user', 'deleted', 'User account has been deleted'),
('user', 'suspended', 'User is temporarily suspended'),

('role', 'inactive', 'Role is not currently in use'),
('role', 'active', 'Role is currently assigned and active'),
('role', 'pending', 'Role approval is pending'),
('role', 'deprecated', 'Role is outdated and should not be used'),
('role', 'deleted', 'Role has been removed'),

('group', 'inactive', 'Group is inactive'),
('group', 'active', 'Group is active'),
('group', 'archived', 'Group is archived for reference'),
('group', 'pending', 'Group creation is pending approval'),
('group', 'banned', 'Group has been banned due to violations'),
('group', 'deleted', 'Group has been deleted'),

('user_group', 'inactive', 'User is inactive in the group'),
('user_group', 'active', 'User is active in the group'),
('user_group', 'pending', 'User request to join is pending'),
('user_group', 'banned', 'User is banned from the group'),
('user_group', 'left', 'User has voluntarily left the group'),
('user_group', 'archived', 'User-group relationship is archived'),

('task', 'pending', 'Task is waiting to be started'),
('task', 'progress', 'Task is in progress'),
('task', 'completed', 'Task is completed'),
('task', 'canceled', 'Task has been canceled'),
('task', 'archived', 'Task is archived for reference'),

('setting', 'inactive', 'Setting is disabled'),
('setting', 'active', 'Setting is enabled'),
('setting', 'pending', 'Setting change is pending approval'),
('setting', 'deprecated', 'Setting is outdated and should not be used'),
('setting', 'archived', 'Setting is archived for reference'),
('setting', 'deleted', 'Setting has been removed');

TRUNCATE TABLE role;
INSERT INTO role (role_name, status)
SELECT 'admin', status_id FROM status WHERE status_type = 'role' AND status_name = 'active'
UNION ALL
SELECT 'user', status_id FROM status WHERE status_type = 'role' AND status_name = 'active'
UNION ALL
SELECT 'moderator', status_id FROM status WHERE status_type = 'role' AND status_name = 'active';
