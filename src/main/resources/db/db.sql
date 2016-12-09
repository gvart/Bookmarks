CREATE TABLE event_eventtypes
(
  id            BIGSERIAL PRIMARY KEY NOT NULL,
  event_id      INTEGER               NOT NULL,
  event_type_id INTEGER               NOT NULL,
  CONSTRAINT event_eventtypes_event_id_fkey FOREIGN KEY (event_id) REFERENCES events (id),
  CONSTRAINT event_eventtypes_event_type_id_fkey FOREIGN KEY (event_type_id) REFERENCES event_types (id)
);
ALTER TABLE event_eventtypes
  ADD PRIMARY KEY (event_id, event_type_id);
CREATE TABLE event_types
(
  id   BIGSERIAL PRIMARY KEY NOT NULL,
  name VARCHAR(100)          NOT NULL
);
CREATE UNIQUE INDEX event_types_name_key ON event_types (name);

CREATE TABLE events
(
  id          BIGSERIAL PRIMARY KEY NOT NULL,
  name        VARCHAR(100)          NOT NULL,
  description VARCHAR(1000)         NOT NULL,
  age         INTEGER DEFAULT 0,
  lat         DOUBLE PRECISION      NOT NULL,
  lng         DOUBLE PRECISION      NOT NULL,
  user_id     INTEGER               NOT NULL,
  private     BOOLEAN DEFAULT FALSE,
  date        DATE                  NOT NULL,
  street      varchar(200)          not null,
  price       DOUBLE PRECISION      DEFAULT 0,
  CONSTRAINT fk_user_id_users_id FOREIGN KEY (user_id) REFERENCES users (id)
);
CREATE TABLE user_roles
(
  user_role_id BIGSERIAL PRIMARY KEY NOT NULL,
  user_id      INTEGER               NOT NULL,
  role         VARCHAR(45)           NOT NULL,
  CONSTRAINT user_roles_user_id_fkey FOREIGN KEY (user_id) REFERENCES users (id)
);
CREATE UNIQUE INDEX uni_userid_role ON user_roles (role, user_id);

CREATE TABLE users
(
  id         BIGSERIAL PRIMARY KEY NOT NULL,
  username   VARCHAR(50)           NOT NULL,
  password   VARCHAR(60)           NOT NULL,
  email      VARCHAR(100),
  firstname  VARCHAR(50),
  lastname   VARCHAR(50),
  createdate DATE,
  enabled    BOOLEAN      DEFAULT TRUE,
  quote      VARCHAR(100) DEFAULT '' :: CHARACTER VARYING
);