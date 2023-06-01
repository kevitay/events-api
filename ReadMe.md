# Events API Endpoints
The below endpoints manage the Event entity.

GET
---
Event API supports Get Event List

`GET /api/event/` returns list of all events (will eventually be all events visible to user)

GET BY ID
---------
Event API supports Get Event by Event ID

`GET /api/event/{id}` returns the single event matching the given ID

response codes: 
- returns 200 - 'Success' when event is found
- returns 204 - 'No Content' when event id not found

POST EVENT
----------
Event API supports Post new Event

`POST /api/event/`

sample request body:
```
{
    "creatorID": "aabbcc1234",
    "organization": "Phils Buds",
    "name": "St. Patricks Bar Crawl '01",
    "type": "Social",
    "description": "Phil's 21st Birthday Pub Crawl",
    "baseCost": "50",
    "status": "planned",
    "public": false
}
```

response body includes created event.

response codes:
- returns 201 - 'Created' when event is created
- returns 400 - 'Bad Request' when request body is invalid

PUT EVENT
---------
Event API supports PUT update entire Event by ID

`POST /api/event/{id}`

sample request body:
```
{
    "id": 2,
    "creatorID": "phil",
    "organization": "Phils Buds",
    "name": "St. Patricks Bar Crawl '01",
    "type": "Social",
    "description": "Phil's 21st Birthday Pub Crawl",
    "baseCost": "50",
    "status": "planned",
    "public": false
}
```

response body includes updated event.

response codes: 
- returns 200 - 'Success' when event is updated
- returns 204 - 'No Content' when event id not found
- returns 400 - 'Bad Request' when request body is invalid

PATCH EVENT
-----------
Event API supports PATCH update for Event Status to allow the owner to change (i.e. Cancel or change from Draft to Planned)
(Potential Future Enhancement to support a patch of the event owner)

`PATCH /api/event/{id}`

sample request body:

`{"status": "upcoming"}`

response body includes updated event.

response codes:
- returns 200 - 'Success' when event is updated
- returns 204 - 'No Content' when event id not found
- returns 400 - 'Bad Request' when request body is invalid


DELETE EVENT
------------
Event API supports DELETE Event by ID. 

`DELETE /api/event/{id}`

response codes: 
- returns 202 - 'Accepted' when event is deleted
- returns 204 - 'No Content' when event id not found