## policies:id.invalid

```json
{
  "topic": "unknown/unknown/policies/errors",
  "headers": {
    "content-type": "application/json"
  },
  "path": "/",
  "value": {
    "status": 400,
    "error": "policies:id.invalid",
    "message": "Policy ID 'invalid id' is not valid!",
    "description": "It must conform to the namespaced entity ID notation (see Ditto documentation)",
    "href": "https://www.eclipse.org/ditto/basic-namespaces-and-names.html#namespaced-id"
  },
  "status": 400
}
```
