### Endpoints
1. Create Drone
2. Turn Drone
3. Move Drone

Currently in memory storage is used for storing drone information.


1 Create Drone
POST /drone/
Desc; creates a drone
Input:{"facingTo" : "EAST"// optional}
Output: {
   "id": "7ab30460-1a05-4a45-a148-fe50345bd61c",// id for the drone
   "facingTo": "NORTH",// current direct facing
   "cordinates": [
   0, // x cordinate on the field
   0 // y cordinate on the field
   ]
   }

2. Turn Drone
Patch /{id}/turn
Input:{
   "turn":"right",
   "op":"change"
   }
Output:{
   "id": "db141131-6599-4b36-81a5-6b56d19ef356",
   "facingTo": "SOUTH",
   "cordinates": [
   0,
   0
   ]
   }
//direction can only be left or right

3. Move
Patch : /{id}/move/{steps}
Response:{
   "id": "02d0dc08-3b4d-4cde-92b4-f6b4e2c687b5",
   "facingTo": "EAST",
   "cordinates": [
   1,
   0
   ]
   }
// If movement is outside the field then it will throw exception 
that movement not possible

Future Implementation:

- Can return the current cordinates instead of throwing exception
- Implement exception advice
- Store the data of field 
- Store the information of drone