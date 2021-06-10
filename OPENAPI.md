# OPEN API Introduction

## 1. REST and OPEN API -- [IBM YouTube intro video](https://www.youtube.com/watch?v=pRS9LRBgjYg)

- **Standardized** Format readable by Humans & Machines
    - Describes resources, endpoints, authentication, operations, parameters...
- Makes it easy to **understand** how to use service
- **Extendable** with tooling: 
    - API validator 
    - API Documentation generator
    - SDK generator (for consuming that API)
- OPEN API is essentially a `YAML` or `JSON` file that describes:
    - General API info
    - API Request Info
    - API Response Info
    - Sample data and example responses
    
## 2. API Description File -- [OPEN API Doc](https://oai.github.io/Documentation/introduction.html)

- An API description file (sometimes called Contract) is a **machine-readable** specification of an API
- Having your API formally described in a machine-readable format allows **automated tools** to process it
- Document Syntax:
    - `openapi.yaml` :
    ```yaml
    # Anything after a hash sign is a comment
    anObject:
    aNumber: 42
    aString: This is a string
    aBoolean: true
    nothing: null
    arrayOfNumbers:
        - 1
        - 2
        - 3
    # or
    arrayOfNumbers: [1, 2, 3]
    ```
- [OpenAPI specification Explained](https://oai.github.io/Documentation/specification.html) - quick intro to how a description file is written

## 3. Add Swagger UI to Spring Boot

Great Video Intro & Tutorial: [Java Brains](https://www.youtube.com/watch?v=gduKpLW_vdY)