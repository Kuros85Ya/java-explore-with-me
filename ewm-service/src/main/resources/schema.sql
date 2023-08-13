drop table if exists requests;
drop table if exists compilation_event;
drop table if exists compilations;
drop table if exists events;
drop table if exists categories;
drop table if exists users;
drop table if exists locations;

create table if not exists categories
(
    id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar unique not null
);

comment on table categories is 'Категории событий';
comment on column categories.id is 'Идентификатор категории';
comment on column categories.name is 'Название категории';

---
create table if not exists locations
(
    id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    latitude float not null,
    longitude float not null,
    description varchar default null,
    UNIQUE (latitude, longitude)
);

comment on table locations is 'Локации';
comment on column locations.id is 'Идентификатор локации';
comment on column locations.latitude is 'Широта локации';
comment on column locations.longitude is 'Долгота локации';
comment on column locations.description is 'Описание локации';

---
create table if not exists users
(
    id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar not null,
    email varchar unique not null,
    favorite_location_id bigint default null,
    last_visited_location_id bigint default null,
    CONSTRAINT fk_users_to_favorite_locations FOREIGN KEY(favorite_location_id) REFERENCES locations(id),
    CONSTRAINT fk_users_to_visited_locations FOREIGN KEY(last_visited_location_id) REFERENCES locations(id)
);

comment on table users is 'Пользователи';
comment on column users.id is 'Идентификатор пользователя';
comment on column users.name is 'Имя пользователя';
comment on column users.email is 'Электронная почта пользователя';
comment on column users.favorite_location_id is 'Любимая точка пользователя (на основе ранее посещенных событий)';
comment on column users.last_visited_location_id is 'Локация последнего посещенного пользователем события';

---
create table if not exists events
(
    id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    annotation varchar not null,
    description varchar not null,
    event_date timestamp not null,
    location_id bigint default null,
    paid boolean default false,
    participant_limit int default 0,
    request_moderation boolean default true,
    title varchar default null,
    category_id bigint default null,
    creator_id bigint default null,
    creation_dttm timestamp not null default now(),
    publication_dttm timestamp default null,
    status varchar not null default 'PENDING',

    CONSTRAINT fk_users_to_categories FOREIGN KEY(category_id) REFERENCES categories(id),
    CONSTRAINT fk_events_to_users FOREIGN KEY(creator_id) REFERENCES users(id),
    CONSTRAINT fk_events_to_locations FOREIGN KEY(location_id) REFERENCES locations(id)
);

---
create table if not exists requests
(
    id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    requester_id bigint not null,
    event_id bigint not null,
    status varchar not null default 'PENDING',
    created timestamp not null default now(),

    CONSTRAINT fk_requests_to_users FOREIGN KEY(requester_id) REFERENCES users(id),
    CONSTRAINT fk_requests_to_events FOREIGN KEY(event_id) REFERENCES events(id)
);

comment on table requests is 'Заявки на участие в событии';
comment on column requests.id is 'Идентификатор заявки';
comment on column requests.requester_id is 'Идентификатор автора заявки';
comment on column requests.event_id is 'Идентификатор события в котором хочется участвовать пользователю';
comment on column requests.status is 'Статус заявки';
comment on column requests.created is 'Дата создания заявки';

---
create table if not exists compilations
(
    id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title varchar not null,
    pinned boolean not null default false
);

comment on table compilations is 'Компиляции событий';
comment on column compilations.id is 'Идентификатор компиляции';
comment on column compilations.title is 'Название компиляции';
comment on column compilations.pinned is 'Признак того, что компиляция закреплена';

---
create table if not exists compilation_event
(
    compilation_id bigint not null,
    event_id bigint not null,
    CONSTRAINT compilations_fk_1 FOREIGN KEY(compilation_id) REFERENCES compilations(id),
    CONSTRAINT compilations_fk_2 FOREIGN KEY(event_id) REFERENCES events(id)
);

comment on table compilations is 'Компиляции событий';
comment on column compilations.id is 'Идентификатор компиляции';
comment on column compilations.title is 'Название компиляции';
comment on column compilations.pinned is 'Признак того, что компиляция закреплена';

---
CREATE OR REPLACE FUNCTION distance(lat1 float, lon1 float, lat2 float, lon2 float)
    RETURNS float
AS
'
    declare
        dist float = 0;
        rad_lat1 float;
        rad_lat2 float;
        theta float;
        rad_theta float;
    BEGIN
        IF lat1 = lat2 AND lon1 = lon2
        THEN
            RETURN dist;
        ELSE
            -- переводим градусы широты в радианы
            rad_lat1 = pi() * lat1 / 180;
            -- переводим градусы долготы в радианы
            rad_lat2 = pi() * lat2 / 180;
            -- находим разность долгот
            theta = lon1 - lon2;
            -- переводим градусы в радианы
            rad_theta = pi() * theta / 180;
            -- находим длину ортодромии
            dist = sin(rad_lat1) * sin(rad_lat2) + cos(rad_lat1) * cos(rad_lat2) * cos(rad_theta);

            IF dist > 1
            THEN dist = 1;
            END IF;

            dist = acos(dist);
            -- переводим радианы в градусы
            dist = dist * 180 / pi();
            -- переводим градусы в километры
            dist = dist * 60 * 1.8524;

            RETURN dist;
        END IF;
    END;
'
    LANGUAGE PLPGSQL;