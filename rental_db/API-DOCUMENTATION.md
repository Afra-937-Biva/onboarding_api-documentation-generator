# Rental Website Backend API Documentation

## Overview

This document contains automatically generated API documentation for the Rental Website Spring Boot backend.

**Total Endpoints:** 29

**Controllers:** 5

**DTOs:** 4

**Entities:** 4

## API Endpoints

### GET /admin

| Property | Value |
|---|---|
| Controller | `AdminController` |
| Method | `dashboard` |
| Description | Dashboard |
| Status | `200` |

#### Response

**Return Type:** `String`

```json
"example"
```

---

### GET /admin/property-types

| Property | Value |
|---|---|
| Controller | `AdminController` |
| Method | `listPropertyTypes` |
| Description | List Property Types |
| Status | `200` |

#### Response

**Return Type:** `String`

```json
"example"
```

---

### GET /admin/property-types/add

| Property | Value |
|---|---|
| Controller | `AdminController` |
| Method | `addPropertyTypeForm` |
| Description | Add Property Type Form |
| Status | `200` |

#### Response

**Return Type:** `String`

```json
"example"
```

---

### GET /admin/property-types/edit/{id}

| Property | Value |
|---|---|
| Controller | `AdminController` |
| Method | `editPropertyTypeForm` |
| Description | Edit Property Type Form |
| Status | `200` |

#### Parameters

| Name | Type | Location | Required |
|---|---|---|---|
| `id` | `Long` | Path | true |

#### Response

**Return Type:** `String`

```json
"example"
```

---

### POST /admin/property-types/save

| Property | Value |
|---|---|
| Controller | `AdminController` |
| Method | `savePropertyType` |
| Description | Save Property Type |
| Status | `200` |

#### Response

**Return Type:** `String`

```json
"example"
```

---

### GET /admin/property-types/delete/{id}

| Property | Value |
|---|---|
| Controller | `AdminController` |
| Method | `deletePropertyType` |
| Description | Delete Property Type |
| Status | `200` |

#### Parameters

| Name | Type | Location | Required |
|---|---|---|---|
| `id` | `Long` | Path | true |

#### Response

**Return Type:** `String`

```json
"example"
```

---

### GET /admin/properties

| Property | Value |
|---|---|
| Controller | `AdminController` |
| Method | `listProperties` |
| Description | List Properties |
| Status | `200` |

#### Response

**Return Type:** `String`

```json
"example"
```

---

### GET /admin/properties/add

| Property | Value |
|---|---|
| Controller | `AdminController` |
| Method | `addPropertyForm` |
| Description | Add Property Form |
| Status | `200` |

#### Response

**Return Type:** `String`

```json
"example"
```

---

### GET /admin/properties/edit/{id}

| Property | Value |
|---|---|
| Controller | `AdminController` |
| Method | `editPropertyForm` |
| Description | Edit Property Form |
| Status | `200` |

#### Parameters

| Name | Type | Location | Required |
|---|---|---|---|
| `id` | `Long` | Path | true |

#### Response

**Return Type:** `String`

```json
"example"
```

---

### POST /admin/properties/save

| Property | Value |
|---|---|
| Controller | `AdminController` |
| Method | `saveProperty` |
| Description | Save Property |
| Status | `200` |

#### Parameters

| Name | Type | Location | Required |
|---|---|---|---|
| `imageUrls` | `String[]` | Query | false |

#### Response

**Return Type:** `String`

```json
"example"
```

---

### GET /admin/properties/delete/{id}

| Property | Value |
|---|---|
| Controller | `AdminController` |
| Method | `deleteProperty` |
| Description | Delete Property |
| Status | `200` |

#### Parameters

| Name | Type | Location | Required |
|---|---|---|---|
| `id` | `Long` | Path | true |

#### Response

**Return Type:** `String`

```json
"example"
```

---

### POST /admin/properties/{id}/images/add

| Property | Value |
|---|---|
| Controller | `AdminController` |
| Method | `addPropertyImage` |
| Description | Add Property Image |
| Status | `200` |

#### Parameters

| Name | Type | Location | Required |
|---|---|---|---|
| `id` | `Long` | Path | true |
| `imageUrl` | `String` | Query | true |

#### Response

**Return Type:** `String`

```json
"example"
```

---

### GET /admin/properties/images/delete/{id}

| Property | Value |
|---|---|
| Controller | `AdminController` |
| Method | `deletePropertyImage` |
| Description | Delete Property Image |
| Status | `200` |

#### Parameters

| Name | Type | Location | Required |
|---|---|---|---|
| `id` | `Long` | Path | true |
| `propertyId` | `Long` | Query | true |

#### Response

**Return Type:** `String`

```json
"example"
```

---

### GET /admin/contacts

| Property | Value |
|---|---|
| Controller | `AdminController` |
| Method | `listContacts` |
| Description | List Contacts |
| Status | `200` |

#### Response

**Return Type:** `String`

```json
"example"
```

---

### GET /admin/contacts/view/{id}

| Property | Value |
|---|---|
| Controller | `AdminController` |
| Method | `viewContact` |
| Description | View Contact |
| Status | `200` |

#### Parameters

| Name | Type | Location | Required |
|---|---|---|---|
| `id` | `Long` | Path | true |

#### Response

**Return Type:** `String`

```json
"example"
```

---

### POST /admin/contacts/update-status/{id}

| Property | Value |
|---|---|
| Controller | `AdminController` |
| Method | `updateContactStatus` |
| Description | Update Contact Status |
| Status | `200` |

#### Parameters

| Name | Type | Location | Required |
|---|---|---|---|
| `id` | `Long` | Path | true |
| `status` | `String` | Query | true |

#### Response

**Return Type:** `String`

```json
"example"
```

---

### GET /admin/contacts/delete/{id}

| Property | Value |
|---|---|
| Controller | `AdminController` |
| Method | `deleteContact` |
| Description | Delete Contact |
| Status | `200` |

#### Parameters

| Name | Type | Location | Required |
|---|---|---|---|
| `id` | `Long` | Path | true |

#### Response

**Return Type:** `String`

```json
"example"
```

---

### POST /api/contact

| Property | Value |
|---|---|
| Controller | `ContactController` |
| Method | `submit` |
| Description | Submit |
| Status | `200` |

#### Request Body

```json
{
  "fullName": "example",
  "email": "example",
  "contactNumber": "example",
  "description": "example",
  "propertyTypeId": 1,
  "bedroom": 1,
  "bathroom": 1,
  "balcony": 1,
  "area": 25000.0,
  "price": 25000.0,
  "address": "example",
  "location": "example",
  "imageTempUrls": "example"
}
```

#### Response

**Response Entity:** `Contact`

**Response Type:** `Contact`

**Response Fields:**

| Field | Type |
|---|---|
| `id` | `Long` |
| `uniqueId` | `String` |
| `fullName` | `String` |
| `email` | `String` |
| `contactNumber` | `String` |
| `description` | `String` |
| `propertyType` | `PropertyType` |
| `bedroom` | `Integer` |
| `bathroom` | `Integer` |
| `balcony` | `Integer` |
| `area` | `Double` |
| `price` | `Double` |
| `address` | `String` |
| `location` | `String` |
| `imageTempUrls` | `String` |
| `status` | `String` |
| `activeStatus` | `Integer` |
| `createdAt` | `LocalDateTime` |

**Entity Reference:** `Contact`

```json
{
  "id": 1,
  "uniqueId": "example",
  "fullName": "example",
  "email": "example",
  "contactNumber": "example",
  "description": "example",
  "propertyType":   {
    "id": 1,
    "name": "example",
    "activeStatus": 1
  },
  "bedroom": 1,
  "bathroom": 1,
  "balcony": 1,
  "area": 25000.0,
  "price": 25000.0,
  "address": "example",
  "location": "example",
  "imageTempUrls": "example",
  "status": "example",
  "activeStatus": 1,
  "createdAt": "2026-01-01T00:00:00"
}
```

---

### POST /api/properties

| Property | Value |
|---|---|
| Controller | `PropertyController` |
| Method | `create` |
| Description | Create |
| Status | `200` |

#### Request Body

```json
{
  "title": "example",
  "description": "example",
  "price": 25000.0,
  "securityDeposit": 25000.0,
  "propertyTypeId": 1,
  "bedroom": 1,
  "bathroom": 1,
  "balcony": 1,
  "area": 25000.0,
  "amenities": "example",
  "address": "example",
  "location": "example"
}
```

#### Response

**Response Entity:** `Property`

**Response Type:** `Property`

**Response Fields:**

| Field | Type |
|---|---|
| `id` | `Long` |
| `uniqueId` | `String` |
| `title` | `String` |
| `description` | `String` |
| `price` | `Double` |
| `securityDeposit` | `Double` |
| `propertyType` | `PropertyType` |
| `bedroom` | `Integer` |
| `bathroom` | `Integer` |
| `balcony` | `Integer` |
| `area` | `Double` |
| `amenities` | `String` |
| `address` | `String` |
| `location` | `String` |
| `isAvailable` | `Boolean` |
| `activeStatus` | `Integer` |
| `createdAt` | `LocalDateTime` |
| `updatedAt` | `LocalDateTime` |

**Entity Reference:** `Property`

```json
{
  "id": 1,
  "uniqueId": "example",
  "title": "example",
  "description": "example",
  "price": 25000.0,
  "securityDeposit": 25000.0,
  "propertyType":   {
    "id": 1,
    "name": "example",
    "activeStatus": 1
  },
  "bedroom": 1,
  "bathroom": 1,
  "balcony": 1,
  "area": 25000.0,
  "amenities": "example",
  "address": "example",
  "location": "example",
  "isAvailable": true,
  "activeStatus": 1,
  "createdAt": "2026-01-01T00:00:00",
  "updatedAt": "2026-01-01T00:00:00"
}
```

---

### GET /api/properties

| Property | Value |
|---|---|
| Controller | `PropertyController` |
| Method | `getAll` |
| Description | Get All |
| Status | `200` |

#### Response

**Response Entity:** `Property`

**Response Type:** `List<Property>`

**Response Fields:**

| Field | Type |
|---|---|
| `id` | `Long` |
| `uniqueId` | `String` |
| `title` | `String` |
| `description` | `String` |
| `price` | `Double` |
| `securityDeposit` | `Double` |
| `propertyType` | `PropertyType` |
| `bedroom` | `Integer` |
| `bathroom` | `Integer` |
| `balcony` | `Integer` |
| `area` | `Double` |
| `amenities` | `String` |
| `address` | `String` |
| `location` | `String` |
| `isAvailable` | `Boolean` |
| `activeStatus` | `Integer` |
| `createdAt` | `LocalDateTime` |
| `updatedAt` | `LocalDateTime` |

**Entity Reference:** `Property`

```json
[
  {
    "id": 1,
    "uniqueId": "example",
    "title": "example",
    "description": "example",
    "price": 25000.0,
    "securityDeposit": 25000.0,
    "propertyType":   {
    "id": 1,
    "name": "example",
    "activeStatus": 1
  },
    "bedroom": 1,
    "bathroom": 1,
    "balcony": 1,
    "area": 25000.0,
    "amenities": "example",
    "address": "example",
    "location": "example",
    "isAvailable": true,
    "activeStatus": 1,
    "createdAt": "2026-01-01T00:00:00",
    "updatedAt": "2026-01-01T00:00:00"
  }
]
```

---

### GET /api/properties/search

| Property | Value |
|---|---|
| Controller | `PropertyController` |
| Method | `searchProperties` |
| Description | Search Properties |
| Status | `200` |

#### Parameters

| Name | Type | Location | Required |
|---|---|---|---|
| `typeId` | `Long` | Query | false |
| `location` | `String` | Query | false |
| `minPrice` | `Double` | Query | false |
| `maxPrice` | `Double` | Query | false |
| `keyword` | `String` | Query | false |

#### Response

**Response Entity:** `Property`

**Response Type:** `List<Property>`

**Response Fields:**

| Field | Type |
|---|---|
| `id` | `Long` |
| `uniqueId` | `String` |
| `title` | `String` |
| `description` | `String` |
| `price` | `Double` |
| `securityDeposit` | `Double` |
| `propertyType` | `PropertyType` |
| `bedroom` | `Integer` |
| `bathroom` | `Integer` |
| `balcony` | `Integer` |
| `area` | `Double` |
| `amenities` | `String` |
| `address` | `String` |
| `location` | `String` |
| `isAvailable` | `Boolean` |
| `activeStatus` | `Integer` |
| `createdAt` | `LocalDateTime` |
| `updatedAt` | `LocalDateTime` |

**Entity Reference:** `Property`

```json
[
  {
    "id": 1,
    "uniqueId": "example",
    "title": "example",
    "description": "example",
    "price": 25000.0,
    "securityDeposit": 25000.0,
    "propertyType":   {
    "id": 1,
    "name": "example",
    "activeStatus": 1
  },
    "bedroom": 1,
    "bathroom": 1,
    "balcony": 1,
    "area": 25000.0,
    "amenities": "example",
    "address": "example",
    "location": "example",
    "isAvailable": true,
    "activeStatus": 1,
    "createdAt": "2026-01-01T00:00:00",
    "updatedAt": "2026-01-01T00:00:00"
  }
]
```

---

### GET /api/properties/{id}

| Property | Value |
|---|---|
| Controller | `PropertyController` |
| Method | `getById` |
| Description | Get By Id |
| Status | `200` |

#### Parameters

| Name | Type | Location | Required |
|---|---|---|---|
| `id` | `Long` | Path | true |

#### Response

**Response Entity:** `Property`

**Response Type:** `Property`

**Response Fields:**

| Field | Type |
|---|---|
| `id` | `Long` |
| `uniqueId` | `String` |
| `title` | `String` |
| `description` | `String` |
| `price` | `Double` |
| `securityDeposit` | `Double` |
| `propertyType` | `PropertyType` |
| `bedroom` | `Integer` |
| `bathroom` | `Integer` |
| `balcony` | `Integer` |
| `area` | `Double` |
| `amenities` | `String` |
| `address` | `String` |
| `location` | `String` |
| `isAvailable` | `Boolean` |
| `activeStatus` | `Integer` |
| `createdAt` | `LocalDateTime` |
| `updatedAt` | `LocalDateTime` |

**Entity Reference:** `Property`

```json
{
  "id": 1,
  "uniqueId": "example",
  "title": "example",
  "description": "example",
  "price": 25000.0,
  "securityDeposit": 25000.0,
  "propertyType":   {
    "id": 1,
    "name": "example",
    "activeStatus": 1
  },
  "bedroom": 1,
  "bathroom": 1,
  "balcony": 1,
  "area": 25000.0,
  "amenities": "example",
  "address": "example",
  "location": "example",
  "isAvailable": true,
  "activeStatus": 1,
  "createdAt": "2026-01-01T00:00:00",
  "updatedAt": "2026-01-01T00:00:00"
}
```

---

### PUT /api/properties/{id}

| Property | Value |
|---|---|
| Controller | `PropertyController` |
| Method | `update` |
| Description | Update |
| Status | `200` |

#### Parameters

| Name | Type | Location | Required |
|---|---|---|---|
| `id` | `Long` | Path | true |

#### Request Body

```json
{
  "title": "example",
  "description": "example",
  "price": 25000.0,
  "securityDeposit": 25000.0,
  "propertyTypeId": 1,
  "bedroom": 1,
  "bathroom": 1,
  "balcony": 1,
  "area": 25000.0,
  "amenities": "example",
  "address": "example",
  "location": "example"
}
```

#### Response

**Response Entity:** `Property`

**Response Type:** `Property`

**Response Fields:**

| Field | Type |
|---|---|
| `id` | `Long` |
| `uniqueId` | `String` |
| `title` | `String` |
| `description` | `String` |
| `price` | `Double` |
| `securityDeposit` | `Double` |
| `propertyType` | `PropertyType` |
| `bedroom` | `Integer` |
| `bathroom` | `Integer` |
| `balcony` | `Integer` |
| `area` | `Double` |
| `amenities` | `String` |
| `address` | `String` |
| `location` | `String` |
| `isAvailable` | `Boolean` |
| `activeStatus` | `Integer` |
| `createdAt` | `LocalDateTime` |
| `updatedAt` | `LocalDateTime` |

**Entity Reference:** `Property`

```json
{
  "id": 1,
  "uniqueId": "example",
  "title": "example",
  "description": "example",
  "price": 25000.0,
  "securityDeposit": 25000.0,
  "propertyType":   {
    "id": 1,
    "name": "example",
    "activeStatus": 1
  },
  "bedroom": 1,
  "bathroom": 1,
  "balcony": 1,
  "area": 25000.0,
  "amenities": "example",
  "address": "example",
  "location": "example",
  "isAvailable": true,
  "activeStatus": 1,
  "createdAt": "2026-01-01T00:00:00",
  "updatedAt": "2026-01-01T00:00:00"
}
```

---

### DELETE /api/properties/{id}

| Property | Value |
|---|---|
| Controller | `PropertyController` |
| Method | `delete` |
| Description | Delete |
| Status | `200` |

#### Parameters

| Name | Type | Location | Required |
|---|---|---|---|
| `id` | `Long` | Path | true |

#### Response

**Return Type:** `String`

```json
"example"
```

---

### POST /api/property-images

| Property | Value |
|---|---|
| Controller | `PropertyImageController` |
| Method | `upload` |
| Description | Upload |
| Status | `200` |

#### Request Body

```json
{
  "propertyId": 1,
  "imageUrl": "example"
}
```

#### Response

**Response Entity:** `PropertyImage`

**Response Type:** `PropertyImage`

**Response Fields:**

| Field | Type |
|---|---|
| `id` | `Long` |
| `property` | `Property` |
| `imageUrl` | `String` |
| `activeStatus` | `Integer` |
| `createdAt` | `LocalDateTime` |

**Entity Reference:** `PropertyImage`

```json
{
  "id": 1,
  "property":   {
    "id": 1,
    "uniqueId": "example",
    "title": "example",
    "description": "example",
    "price": 25000.0,
    "securityDeposit": 25000.0,
    "propertyType": {"id": 1},
    "bedroom": 1,
    "bathroom": 1,
    "balcony": 1,
    "area": 25000.0,
    "amenities": "example",
    "address": "example",
    "location": "example",
    "isAvailable": true,
    "activeStatus": 1,
    "createdAt": "2026-01-01T00:00:00",
    "updatedAt": "2026-01-01T00:00:00"
  },
  "imageUrl": "example",
  "activeStatus": 1,
  "createdAt": "2026-01-01T00:00:00"
}
```

---

### POST /api/property-types

| Property | Value |
|---|---|
| Controller | `PropertyTypeController` |
| Method | `create` |
| Description | Create |
| Status | `200` |

#### Request Body

```json
{
  "id": 1,
  "name": "example"
}
```

#### Response

**Response Entity:** `PropertyType`

**Response Type:** `PropertyType`

**Response Fields:**

| Field | Type |
|---|---|
| `id` | `Long` |
| `name` | `String` |
| `activeStatus` | `Integer` |

**Entity Reference:** `PropertyType`

```json
{
  "id": 1,
  "name": "example",
  "activeStatus": 1
}
```

---

### GET /api/property-types

| Property | Value |
|---|---|
| Controller | `PropertyTypeController` |
| Method | `getAll` |
| Description | Get All |
| Status | `200` |

#### Response

**Response Entity:** `PropertyType`

**Response Type:** `List<PropertyType>`

**Response Fields:**

| Field | Type |
|---|---|
| `id` | `Long` |
| `name` | `String` |
| `activeStatus` | `Integer` |

**Entity Reference:** `PropertyType`

```json
[
  {
    "id": 1,
    "name": "example",
    "activeStatus": 1
  }
]
```

---

### PUT /api/property-types/{id}

| Property | Value |
|---|---|
| Controller | `PropertyTypeController` |
| Method | `update` |
| Description | Update |
| Status | `200` |

#### Parameters

| Name | Type | Location | Required |
|---|---|---|---|
| `id` | `Long` | Path | true |

#### Request Body

```json
{
  "id": 1,
  "name": "example"
}
```

#### Response

**Response Entity:** `PropertyType`

**Response Type:** `PropertyType`

**Response Fields:**

| Field | Type |
|---|---|
| `id` | `Long` |
| `name` | `String` |
| `activeStatus` | `Integer` |

**Entity Reference:** `PropertyType`

```json
{
  "id": 1,
  "name": "example",
  "activeStatus": 1
}
```

---

### DELETE /api/property-types/{id}

| Property | Value |
|---|---|
| Controller | `PropertyTypeController` |
| Method | `delete` |
| Description | Delete |
| Status | `200` |

#### Parameters

| Name | Type | Location | Required |
|---|---|---|---|
| `id` | `Long` | Path | true |

#### Response

**Return Type:** `String`

```json
"example"
```

---

## DTO Documentation

### ContactDTO

| Field | Type |
|---|---|
| `fullName` | `String` |
| `email` | `String` |
| `contactNumber` | `String` |
| `description` | `String` |
| `propertyTypeId` | `Long` |
| `bedroom` | `Integer` |
| `bathroom` | `Integer` |
| `balcony` | `Integer` |
| `area` | `Double` |
| `price` | `Double` |
| `address` | `String` |
| `location` | `String` |
| `imageTempUrls` | `String` |

### PropertyImageDTO

| Field | Type |
|---|---|
| `propertyId` | `Long` |
| `imageUrl` | `String` |

### PropertyTypeDTO

| Field | Type |
|---|---|
| `id` | `Long` |
| `name` | `String` |

### PropertyDTO

| Field | Type |
|---|---|
| `title` | `String` |
| `description` | `String` |
| `price` | `Double` |
| `securityDeposit` | `Double` |
| `propertyTypeId` | `Long` |
| `bedroom` | `Integer` |
| `bathroom` | `Integer` |
| `balcony` | `Integer` |
| `area` | `Double` |
| `amenities` | `String` |
| `address` | `String` |
| `location` | `String` |

## Entity Documentation

### Contact

| Field | Type |
|---|---|
| `id` | `Long` |
| `uniqueId` | `String` |
| `fullName` | `String` |
| `email` | `String` |
| `contactNumber` | `String` |
| `description` | `String` |
| `propertyType` | `PropertyType` |
| `bedroom` | `Integer` |
| `bathroom` | `Integer` |
| `balcony` | `Integer` |
| `area` | `Double` |
| `price` | `Double` |
| `address` | `String` |
| `location` | `String` |
| `imageTempUrls` | `String` |
| `status` | `String` |
| `activeStatus` | `Integer` |
| `createdAt` | `LocalDateTime` |

### Property

| Field | Type |
|---|---|
| `id` | `Long` |
| `uniqueId` | `String` |
| `title` | `String` |
| `description` | `String` |
| `price` | `Double` |
| `securityDeposit` | `Double` |
| `propertyType` | `PropertyType` |
| `bedroom` | `Integer` |
| `bathroom` | `Integer` |
| `balcony` | `Integer` |
| `area` | `Double` |
| `amenities` | `String` |
| `address` | `String` |
| `location` | `String` |
| `isAvailable` | `Boolean` |
| `activeStatus` | `Integer` |
| `createdAt` | `LocalDateTime` |
| `updatedAt` | `LocalDateTime` |

### PropertyImage

| Field | Type |
|---|---|
| `id` | `Long` |
| `property` | `Property` |
| `imageUrl` | `String` |
| `activeStatus` | `Integer` |
| `createdAt` | `LocalDateTime` |

### PropertyType

| Field | Type |
|---|---|
| `id` | `Long` |
| `name` | `String` |
| `activeStatus` | `Integer` |

## Entity Relationships

Entity relationships detected from JPA annotations.

- `Contact.propertyType` → `PropertyType`
- `Property.propertyType` → `PropertyType`
- `PropertyImage.property` → `Property`

## Frontend Integration

The generated API documentation can be consumed by a Vue 3 or other frontend application.

The JSON documentation file contains structured information about controllers, endpoints, DTOs, entities and relationships.

Generated JSON file:

`api-documentation.json`

## Notes

- Documentation is generated automatically from Java source files.
- Endpoint information is extracted from Spring MVC annotations.
- DTO information is extracted from DTO classes.
- Entity information is extracted from JPA entity classes.
- Entity relationships are detected from JPA relationship annotations.
- Response examples are generated from detected entity fields.
- Collection responses such as List<Entity> are generated as JSON arrays.
- Nested entity examples are limited to one level to avoid circular references.
