CREATE TABLE event_eventtypes
(
    id BIGINT PRIMARY KEY NOT NULL,
    event_id INTEGER NOT NULL,
    event_type_id INTEGER NOT NULL,
    CONSTRAINT event_eventtypes_event_id_fkey FOREIGN KEY (event_id) REFERENCES ,
    CONSTRAINT event_eventtypes_event_type_id_fkey FOREIGN KEY (event_type_id) REFERENCES event_types (id)
);
CREATE TABLE event_types
(
    id BIGINT PRIMARY KEY NOT NULL,
    name VARCHAR(100) NOT NULL
);
CREATE UNIQUE INDEX event_types_name_key ON event_types (name);
CREATE TABLE events
(
    id BIGINT PRIMARY KEY NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    age INTEGER DEFAULT 0,
    lat DOUBLE PRECISION NOT NULL,
    lang DOUBLE PRECISION NOT NULL,
    user_id INTEGER NOT NULL,
    private BOOLEAN DEFAULT false,
    date DATE NOT NULL,
    CONSTRAINT fk_user_id_users_id FOREIGN KEY (user_id) REFERENCES users (id)
);
CREATE TABLE user_roles
(
    user_role_id BIGINT PRIMARY KEY NOT NULL,
    user_id INTEGER NOT NULL,
    role VARCHAR(45) NOT NULL,
    CONSTRAINT user_roles_user_id_fkey FOREIGN KEY (user_id) REFERENCES
);
CREATE UNIQUE INDEX uni_userid_role ON user_roles (role, user_id);
CREATE TABLE users
(
    id BIGINT PRIMARY KEY NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(60) NOT NULL,
    email VARCHAR(100),
    firstname VARCHAR(50),
    lastname VARCHAR(50),
    createdate DATE,
    enabled BOOLEAN DEFAULT true,
    quote VARCHAR(100) DEFAULT ''::character varying
);