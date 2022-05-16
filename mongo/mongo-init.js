db.createCollection('events', { capped: false });
db.createUser(
    {
        user: "sportsbook",
        pwd: "changeme",
        roles: [
            {
                role: "readWrite",
                db: "sportsbook"
            }
        ]
    }
);
