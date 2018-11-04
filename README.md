# graph-handler-draft

## By running the following bash shell script:
```
#!/bin/sh

# graph_handler_draft.sh
# Graph entity
curl -i -v -H "Content-Type:application/json" -d '{"label":"G1"}' http://127.0.0.1:9090/api/graphs

# retrieve graph labelled G1
curl -i -v http://127.0.0.1:9090/api/graphs

# Vertex entities
VERTEXES_ARRAY=( 
	'{"index":"0","label":"VS","source":"true"}' 
	'{"index":"1","label":"V1"}' 
	'{"index":"2","label":"V2"}' 
	'{"index":"3","label":"V3"}' 
	'{"index":"4","label":"V4"}' 
	'{"index":"5","label":"V5"}' 
	'{"index":"6","label":"VT","sink":"true"}')

VERTEXES_ELEMENTS=${#VERTEXES_ARRAY[@]}

for ((i=0;i<$VERTEXES_ELEMENTS;i++)); do
	curl -i -v -H "Content-Type:application/json" -d ${VERTEXES_ARRAY[${i}]} http://127.0.0.1:9090/api/vertexes
done

# retrieve the list of vertexes
curl -i -v http://127.0.0.1:9090/api/vertexes

# Arch entities
ARCHES_ARRAY=(
	'{"label":"A1","previous":"0","following":"1","capacity":"3.0"}' 
	'{"label":"A2","previous":"0","following":"2","capacity":"3.0"}' 
	'{"label":"A3","previous":"0","following":"3","capacity":"5.0"}' 
	'{"label":"A4","previous":"1","following":"4","capacity":"3.0"}' 
	'{"label":"A5","previous":"1","following":"3","capacity":"4.0"}' 
	'{"label":"A6","previous":"2","following":"5","capacity":"2.0"}' 
	'{"label":"A7","previous":"2","following":"3","capacity":"2.0"}' 
	'{"label":"A8","previous":"3","following":"4","capacity":"2.0"}' 
	'{"label":"A9","previous":"3","following":"5","capacity":"4.0"}' 
	'{"label":"A10","previous":"3","following":"6","capacity":"1.0"}' 
	'{"label":"A11","previous":"4","following":"6","capacity":"4.0"}' 
	'{"label":"A12","previous":"5","following":"6","capacity":"4.0"}')

ARCHES_ELEMENTS=${#ARCHES_ARRAY[@]}

for ((i=0;i<$ARCHES_ELEMENTS;i++)); do 
	curl -i -v -H "Content-Type:application/json" -d ${ARCHES_ARRAY[${i}]} http://127.0.0.1:9090/api/arches
done

# retrieve the list of arches
curl -i -v http://127.0.0.1:9090/api/arches

# Assign the correlations

for ((i=0,j=2;i<$VERTEXES_ELEMENTS;i++,j++)); do
	curl -v -i -X PUT -H "Content-Type:text/uri-list" -d "http://127.0.0.1:9090/api/graphs/1" http://127.0.0.1:9090/api/vertexes/${j}/graph
done

for ((i=0,j=9;i<$ARCHES_ELEMENTS;i++,j++)); do
	curl -v -i -X PUT -H "Content-Type:text/uri-list" -d "http://127.0.0.1:9090/api/graphs/1" http://127.0.0.1:9090/api/arches/${j}/graph
done

# view all graph G1 property
curl -i -v http://127.0.0.1:9090/api/graphs
curl -i -v http://127.0.0.1:9090/api/graphs/1/vertexes
curl -i -v http://127.0.0.1:9090/api/graphs/1/arches
```

## I get the following answers:
```
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> POST /api/graphs HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:application/json
> Content-Length: 14
> 
* upload completely sent off: 14 out of 14 bytes
< HTTP/1.1 201 
HTTP/1.1 201 
< Location: http://127.0.0.1:9090/api/graphs/1
Location: http://127.0.0.1:9090/api/graphs/1
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Sat, 03 Nov 2018 05:49:07 GMT
Date: Sat, 03 Nov 2018 05:49:07 GMT

< 
{
  "label" : "G1",
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/graphs/1"
    },
    "graph" : {
      "href" : "http://127.0.0.1:9090/api/graphs/1"
    },
    "arches" : {
      "href" : "http://127.0.0.1:9090/api/graphs/1/arches"
    },
    "vertexes" : {
      "href" : "http://127.0.0.1:9090/api/graphs/1/vertexes"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> GET /api/graphs HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> 
< HTTP/1.1 200 
HTTP/1.1 200 
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Sat, 03 Nov 2018 05:49:07 GMT
Date: Sat, 03 Nov 2018 05:49:07 GMT

< 
{
  "_embedded" : {
    "graphs" : [ {
      "label" : "G1",
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/graphs/1"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/graphs/1"
        },
        "arches" : {
          "href" : "http://127.0.0.1:9090/api/graphs/1/arches"
        },
        "vertexes" : {
          "href" : "http://127.0.0.1:9090/api/graphs/1/vertexes"
        }
      }
    } ]
  },
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/graphs"
    },
    "profile" : {
      "href" : "http://127.0.0.1:9090/api/profile/graphs"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> POST /api/vertexes HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:application/json
> Content-Length: 42
> 
* upload completely sent off: 42 out of 42 bytes
< HTTP/1.1 201 
HTTP/1.1 201 
< Location: http://127.0.0.1:9090/api/vertexes/2
Location: http://127.0.0.1:9090/api/vertexes/2
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Sat, 03 Nov 2018 05:49:07 GMT
Date: Sat, 03 Nov 2018 05:49:07 GMT

< 
{
  "label" : "VS",
  "index" : 0,
  "source" : true,
  "sink" : false,
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/vertexes/2"
    },
    "vertex" : {
      "href" : "http://127.0.0.1:9090/api/vertexes/2"
    },
    "graph" : {
      "href" : "http://127.0.0.1:9090/api/vertexes/2/graph"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> POST /api/vertexes HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:application/json
> Content-Length: 26
> 
* upload completely sent off: 26 out of 26 bytes
< HTTP/1.1 201 
HTTP/1.1 201 
< Location: http://127.0.0.1:9090/api/vertexes/3
Location: http://127.0.0.1:9090/api/vertexes/3
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Sat, 03 Nov 2018 05:49:07 GMT
Date: Sat, 03 Nov 2018 05:49:07 GMT

< 
{
  "label" : "V1",
  "index" : 1,
  "source" : false,
  "sink" : false,
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/vertexes/3"
    },
    "vertex" : {
      "href" : "http://127.0.0.1:9090/api/vertexes/3"
    },
    "graph" : {
      "href" : "http://127.0.0.1:9090/api/vertexes/3/graph"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> POST /api/vertexes HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:application/json
> Content-Length: 26
> 
* upload completely sent off: 26 out of 26 bytes
< HTTP/1.1 201 
HTTP/1.1 201 
< Location: http://127.0.0.1:9090/api/vertexes/4
Location: http://127.0.0.1:9090/api/vertexes/4
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
{
  "label" : "V2",
  "index" : 2,
  "source" : false,
  "sink" : false,
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/vertexes/4"
    },
    "vertex" : {
      "href" : "http://127.0.0.1:9090/api/vertexes/4"
    },
    "graph" : {
      "href" : "http://127.0.0.1:9090/api/vertexes/4/graph"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> POST /api/vertexes HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:application/json
> Content-Length: 26
> 
* upload completely sent off: 26 out of 26 bytes
< HTTP/1.1 201 
HTTP/1.1 201 
< Location: http://127.0.0.1:9090/api/vertexes/5
Location: http://127.0.0.1:9090/api/vertexes/5
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
{
  "label" : "V3",
  "index" : 3,
  "source" : false,
  "sink" : false,
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/vertexes/5"
    },
    "vertex" : {
      "href" : "http://127.0.0.1:9090/api/vertexes/5"
    },
    "graph" : {
      "href" : "http://127.0.0.1:9090/api/vertexes/5/graph"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> POST /api/vertexes HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:application/json
> Content-Length: 26
> 
* upload completely sent off: 26 out of 26 bytes
< HTTP/1.1 201 
HTTP/1.1 201 
< Location: http://127.0.0.1:9090/api/vertexes/6
Location: http://127.0.0.1:9090/api/vertexes/6
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
{
  "label" : "V4",
  "index" : 4,
  "source" : false,
  "sink" : false,
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/vertexes/6"
    },
    "vertex" : {
      "href" : "http://127.0.0.1:9090/api/vertexes/6"
    },
    "graph" : {
      "href" : "http://127.0.0.1:9090/api/vertexes/6/graph"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> POST /api/vertexes HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:application/json
> Content-Length: 26
> 
* upload completely sent off: 26 out of 26 bytes
< HTTP/1.1 201 
HTTP/1.1 201 
< Location: http://127.0.0.1:9090/api/vertexes/7
Location: http://127.0.0.1:9090/api/vertexes/7
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
{
  "label" : "V5",
  "index" : 5,
  "source" : false,
  "sink" : false,
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/vertexes/7"
    },
    "vertex" : {
      "href" : "http://127.0.0.1:9090/api/vertexes/7"
    },
    "graph" : {
      "href" : "http://127.0.0.1:9090/api/vertexes/7/graph"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> POST /api/vertexes HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:application/json
> Content-Length: 40
> 
* upload completely sent off: 40 out of 40 bytes
< HTTP/1.1 201 
HTTP/1.1 201 
< Location: http://127.0.0.1:9090/api/vertexes/8
Location: http://127.0.0.1:9090/api/vertexes/8
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
{
  "label" : "VT",
  "index" : 6,
  "source" : false,
  "sink" : true,
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/vertexes/8"
    },
    "vertex" : {
      "href" : "http://127.0.0.1:9090/api/vertexes/8"
    },
    "graph" : {
      "href" : "http://127.0.0.1:9090/api/vertexes/8/graph"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> GET /api/vertexes HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> 
< HTTP/1.1 200 
HTTP/1.1 200 
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
{
  "_embedded" : {
    "vertexes" : [ {
      "label" : "VS",
      "index" : 0,
      "source" : true,
      "sink" : false,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/2"
        },
        "vertex" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/2"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/2/graph"
        }
      }
    }, {
      "label" : "V1",
      "index" : 1,
      "source" : false,
      "sink" : false,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/3"
        },
        "vertex" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/3"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/3/graph"
        }
      }
    }, {
      "label" : "V2",
      "index" : 2,
      "source" : false,
      "sink" : false,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/4"
        },
        "vertex" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/4"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/4/graph"
        }
      }
    }, {
      "label" : "V3",
      "index" : 3,
      "source" : false,
      "sink" : false,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/5"
        },
        "vertex" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/5"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/5/graph"
        }
      }
    }, {
      "label" : "V4",
      "index" : 4,
      "source" : false,
      "sink" : false,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/6"
        },
        "vertex" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/6"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/6/graph"
        }
      }
    }, {
      "label" : "V5",
      "index" : 5,
      "source" : false,
      "sink" : false,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/7"
        },
        "vertex" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/7"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/7/graph"
        }
      }
    }, {
      "label" : "VT",
      "index" : 6,
      "source" : false,
      "sink" : true,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/8"
        },
        "vertex" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/8"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/8/graph"
        }
      }
    } ]
  },
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/vertexes"
    },
    "profile" : {
      "href" : "http://127.0.0.1:9090/api/profile/vertexes"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> POST /api/arches HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:application/json
> Content-Length: 62
> 
* upload completely sent off: 62 out of 62 bytes
< HTTP/1.1 201 
HTTP/1.1 201 
< Location: http://127.0.0.1:9090/api/arches/9
Location: http://127.0.0.1:9090/api/arches/9
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
{
  "label" : "A1",
  "previous" : 0,
  "following" : 1,
  "flow" : 0.0,
  "capacity" : 3.0,
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/arches/9"
    },
    "arch" : {
      "href" : "http://127.0.0.1:9090/api/arches/9"
    },
    "graph" : {
      "href" : "http://127.0.0.1:9090/api/arches/9/graph"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> POST /api/arches HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:application/json
> Content-Length: 62
> 
* upload completely sent off: 62 out of 62 bytes
< HTTP/1.1 201 
HTTP/1.1 201 
< Location: http://127.0.0.1:9090/api/arches/10
Location: http://127.0.0.1:9090/api/arches/10
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
{
  "label" : "A2",
  "previous" : 0,
  "following" : 2,
  "flow" : 0.0,
  "capacity" : 3.0,
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/arches/10"
    },
    "arch" : {
      "href" : "http://127.0.0.1:9090/api/arches/10"
    },
    "graph" : {
      "href" : "http://127.0.0.1:9090/api/arches/10/graph"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> POST /api/arches HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:application/json
> Content-Length: 62
> 
* upload completely sent off: 62 out of 62 bytes
< HTTP/1.1 201 
HTTP/1.1 201 
< Location: http://127.0.0.1:9090/api/arches/11
Location: http://127.0.0.1:9090/api/arches/11
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
{
  "label" : "A3",
  "previous" : 0,
  "following" : 3,
  "flow" : 0.0,
  "capacity" : 5.0,
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/arches/11"
    },
    "arch" : {
      "href" : "http://127.0.0.1:9090/api/arches/11"
    },
    "graph" : {
      "href" : "http://127.0.0.1:9090/api/arches/11/graph"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> POST /api/arches HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:application/json
> Content-Length: 62
> 
* upload completely sent off: 62 out of 62 bytes
< HTTP/1.1 201 
HTTP/1.1 201 
< Location: http://127.0.0.1:9090/api/arches/12
Location: http://127.0.0.1:9090/api/arches/12
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
{
  "label" : "A4",
  "previous" : 1,
  "following" : 4,
  "flow" : 0.0,
  "capacity" : 3.0,
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/arches/12"
    },
    "arch" : {
      "href" : "http://127.0.0.1:9090/api/arches/12"
    },
    "graph" : {
      "href" : "http://127.0.0.1:9090/api/arches/12/graph"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> POST /api/arches HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:application/json
> Content-Length: 62
> 
* upload completely sent off: 62 out of 62 bytes
< HTTP/1.1 201 
HTTP/1.1 201 
< Location: http://127.0.0.1:9090/api/arches/13
Location: http://127.0.0.1:9090/api/arches/13
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
{
  "label" : "A5",
  "previous" : 1,
  "following" : 3,
  "flow" : 0.0,
  "capacity" : 4.0,
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/arches/13"
    },
    "arch" : {
      "href" : "http://127.0.0.1:9090/api/arches/13"
    },
    "graph" : {
      "href" : "http://127.0.0.1:9090/api/arches/13/graph"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> POST /api/arches HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:application/json
> Content-Length: 62
> 
* upload completely sent off: 62 out of 62 bytes
< HTTP/1.1 201 
HTTP/1.1 201 
< Location: http://127.0.0.1:9090/api/arches/14
Location: http://127.0.0.1:9090/api/arches/14
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
{
  "label" : "A6",
  "previous" : 2,
  "following" : 5,
  "flow" : 0.0,
  "capacity" : 2.0,
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/arches/14"
    },
    "arch" : {
      "href" : "http://127.0.0.1:9090/api/arches/14"
    },
    "graph" : {
      "href" : "http://127.0.0.1:9090/api/arches/14/graph"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> POST /api/arches HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:application/json
> Content-Length: 62
> 
* upload completely sent off: 62 out of 62 bytes
< HTTP/1.1 201 
HTTP/1.1 201 
< Location: http://127.0.0.1:9090/api/arches/15
Location: http://127.0.0.1:9090/api/arches/15
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
{
  "label" : "A7",
  "previous" : 2,
  "following" : 3,
  "flow" : 0.0,
  "capacity" : 2.0,
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/arches/15"
    },
    "arch" : {
      "href" : "http://127.0.0.1:9090/api/arches/15"
    },
    "graph" : {
      "href" : "http://127.0.0.1:9090/api/arches/15/graph"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> POST /api/arches HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:application/json
> Content-Length: 62
> 
* upload completely sent off: 62 out of 62 bytes
< HTTP/1.1 201 
HTTP/1.1 201 
< Location: http://127.0.0.1:9090/api/arches/16
Location: http://127.0.0.1:9090/api/arches/16
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
{
  "label" : "A8",
  "previous" : 3,
  "following" : 4,
  "flow" : 0.0,
  "capacity" : 2.0,
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/arches/16"
    },
    "arch" : {
      "href" : "http://127.0.0.1:9090/api/arches/16"
    },
    "graph" : {
      "href" : "http://127.0.0.1:9090/api/arches/16/graph"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> POST /api/arches HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:application/json
> Content-Length: 62
> 
* upload completely sent off: 62 out of 62 bytes
< HTTP/1.1 201 
HTTP/1.1 201 
< Location: http://127.0.0.1:9090/api/arches/17
Location: http://127.0.0.1:9090/api/arches/17
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
{
  "label" : "A9",
  "previous" : 3,
  "following" : 5,
  "flow" : 0.0,
  "capacity" : 4.0,
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/arches/17"
    },
    "arch" : {
      "href" : "http://127.0.0.1:9090/api/arches/17"
    },
    "graph" : {
      "href" : "http://127.0.0.1:9090/api/arches/17/graph"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> POST /api/arches HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:application/json
> Content-Length: 63
> 
* upload completely sent off: 63 out of 63 bytes
< HTTP/1.1 201 
HTTP/1.1 201 
< Location: http://127.0.0.1:9090/api/arches/18
Location: http://127.0.0.1:9090/api/arches/18
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
{
  "label" : "A10",
  "previous" : 3,
  "following" : 6,
  "flow" : 0.0,
  "capacity" : 1.0,
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/arches/18"
    },
    "arch" : {
      "href" : "http://127.0.0.1:9090/api/arches/18"
    },
    "graph" : {
      "href" : "http://127.0.0.1:9090/api/arches/18/graph"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> POST /api/arches HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:application/json
> Content-Length: 63
> 
* upload completely sent off: 63 out of 63 bytes
< HTTP/1.1 201 
HTTP/1.1 201 
< Location: http://127.0.0.1:9090/api/arches/19
Location: http://127.0.0.1:9090/api/arches/19
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
{
  "label" : "A11",
  "previous" : 4,
  "following" : 6,
  "flow" : 0.0,
  "capacity" : 4.0,
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/arches/19"
    },
    "arch" : {
      "href" : "http://127.0.0.1:9090/api/arches/19"
    },
    "graph" : {
      "href" : "http://127.0.0.1:9090/api/arches/19/graph"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> POST /api/arches HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:application/json
> Content-Length: 63
> 
* upload completely sent off: 63 out of 63 bytes
< HTTP/1.1 201 
HTTP/1.1 201 
< Location: http://127.0.0.1:9090/api/arches/20
Location: http://127.0.0.1:9090/api/arches/20
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
{
  "label" : "A12",
  "previous" : 5,
  "following" : 6,
  "flow" : 0.0,
  "capacity" : 4.0,
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/arches/20"
    },
    "arch" : {
      "href" : "http://127.0.0.1:9090/api/arches/20"
    },
    "graph" : {
      "href" : "http://127.0.0.1:9090/api/arches/20/graph"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> GET /api/arches HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> 
< HTTP/1.1 200 
HTTP/1.1 200 
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
{
  "_embedded" : {
    "arches" : [ {
      "label" : "A1",
      "previous" : 0,
      "following" : 1,
      "flow" : 0.0,
      "capacity" : 3.0,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/arches/9"
        },
        "arch" : {
          "href" : "http://127.0.0.1:9090/api/arches/9"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/arches/9/graph"
        }
      }
    }, {
      "label" : "A2",
      "previous" : 0,
      "following" : 2,
      "flow" : 0.0,
      "capacity" : 3.0,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/arches/10"
        },
        "arch" : {
          "href" : "http://127.0.0.1:9090/api/arches/10"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/arches/10/graph"
        }
      }
    }, {
      "label" : "A3",
      "previous" : 0,
      "following" : 3,
      "flow" : 0.0,
      "capacity" : 5.0,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/arches/11"
        },
        "arch" : {
          "href" : "http://127.0.0.1:9090/api/arches/11"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/arches/11/graph"
        }
      }
    }, {
      "label" : "A4",
      "previous" : 1,
      "following" : 4,
      "flow" : 0.0,
      "capacity" : 3.0,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/arches/12"
        },
        "arch" : {
          "href" : "http://127.0.0.1:9090/api/arches/12"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/arches/12/graph"
        }
      }
    }, {
      "label" : "A5",
      "previous" : 1,
      "following" : 3,
      "flow" : 0.0,
      "capacity" : 4.0,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/arches/13"
        },
        "arch" : {
          "href" : "http://127.0.0.1:9090/api/arches/13"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/arches/13/graph"
        }
      }
    }, {
      "label" : "A6",
      "previous" : 2,
      "following" : 5,
      "flow" : 0.0,
      "capacity" : 2.0,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/arches/14"
        },
        "arch" : {
          "href" : "http://127.0.0.1:9090/api/arches/14"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/arches/14/graph"
        }
      }
    }, {
      "label" : "A7",
      "previous" : 2,
      "following" : 3,
      "flow" : 0.0,
      "capacity" : 2.0,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/arches/15"
        },
        "arch" : {
          "href" : "http://127.0.0.1:9090/api/arches/15"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/arches/15/graph"
        }
      }
    }, {
      "label" : "A8",
      "previous" : 3,
      "following" : 4,
      "flow" : 0.0,
      "capacity" : 2.0,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/arches/16"
        },
        "arch" : {
          "href" : "http://127.0.0.1:9090/api/arches/16"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/arches/16/graph"
        }
      }
    }, {
      "label" : "A9",
      "previous" : 3,
      "following" : 5,
      "flow" : 0.0,
      "capacity" : 4.0,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/arches/17"
        },
        "arch" : {
          "href" : "http://127.0.0.1:9090/api/arches/17"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/arches/17/graph"
        }
      }
    }, {
      "label" : "A10",
      "previous" : 3,
      "following" : 6,
      "flow" : 0.0,
      "capacity" : 1.0,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/arches/18"
        },
        "arch" : {
          "href" : "http://127.0.0.1:9090/api/arches/18"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/arches/18/graph"
        }
      }
    }, {
      "label" : "A11",
      "previous" : 4,
      "following" : 6,
      "flow" : 0.0,
      "capacity" : 4.0,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/arches/19"
        },
        "arch" : {
          "href" : "http://127.0.0.1:9090/api/arches/19"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/arches/19/graph"
        }
      }
    }, {
      "label" : "A12",
      "previous" : 5,
      "following" : 6,
      "flow" : 0.0,
      "capacity" : 4.0,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/arches/20"
        },
        "arch" : {
          "href" : "http://127.0.0.1:9090/api/arches/20"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/arches/20/graph"
        }
      }
    } ]
  },
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/arches"
    },
    "profile" : {
      "href" : "http://127.0.0.1:9090/api/profile/arches"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> PUT /api/vertexes/2/graph HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:text/uri-list
> Content-Length: 34
> 
* upload completely sent off: 34 out of 34 bytes
< HTTP/1.1 204 
HTTP/1.1 204 
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
* Connection #0 to host 127.0.0.1 left intact
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> PUT /api/vertexes/3/graph HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:text/uri-list
> Content-Length: 34
> 
* upload completely sent off: 34 out of 34 bytes
< HTTP/1.1 204 
HTTP/1.1 204 
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
* Connection #0 to host 127.0.0.1 left intact
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> PUT /api/vertexes/4/graph HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:text/uri-list
> Content-Length: 34
> 
* upload completely sent off: 34 out of 34 bytes
< HTTP/1.1 204 
HTTP/1.1 204 
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
* Connection #0 to host 127.0.0.1 left intact
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> PUT /api/vertexes/5/graph HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:text/uri-list
> Content-Length: 34
> 
* upload completely sent off: 34 out of 34 bytes
< HTTP/1.1 204 
HTTP/1.1 204 
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
* Connection #0 to host 127.0.0.1 left intact
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> PUT /api/vertexes/6/graph HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:text/uri-list
> Content-Length: 34
> 
* upload completely sent off: 34 out of 34 bytes
< HTTP/1.1 204 
HTTP/1.1 204 
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
* Connection #0 to host 127.0.0.1 left intact
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> PUT /api/vertexes/7/graph HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:text/uri-list
> Content-Length: 34
> 
* upload completely sent off: 34 out of 34 bytes
< HTTP/1.1 204 
HTTP/1.1 204 
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
* Connection #0 to host 127.0.0.1 left intact
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> PUT /api/vertexes/8/graph HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:text/uri-list
> Content-Length: 34
> 
* upload completely sent off: 34 out of 34 bytes
< HTTP/1.1 204 
HTTP/1.1 204 
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
* Connection #0 to host 127.0.0.1 left intact
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> PUT /api/arches/9/graph HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:text/uri-list
> Content-Length: 34
> 
* upload completely sent off: 34 out of 34 bytes
< HTTP/1.1 204 
HTTP/1.1 204 
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
* Connection #0 to host 127.0.0.1 left intact
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> PUT /api/arches/10/graph HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:text/uri-list
> Content-Length: 34
> 
* upload completely sent off: 34 out of 34 bytes
< HTTP/1.1 204 
HTTP/1.1 204 
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
* Connection #0 to host 127.0.0.1 left intact
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> PUT /api/arches/11/graph HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:text/uri-list
> Content-Length: 34
> 
* upload completely sent off: 34 out of 34 bytes
< HTTP/1.1 204 
HTTP/1.1 204 
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
* Connection #0 to host 127.0.0.1 left intact
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> PUT /api/arches/12/graph HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:text/uri-list
> Content-Length: 34
> 
* upload completely sent off: 34 out of 34 bytes
< HTTP/1.1 204 
HTTP/1.1 204 
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
* Connection #0 to host 127.0.0.1 left intact
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> PUT /api/arches/13/graph HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:text/uri-list
> Content-Length: 34
> 
* upload completely sent off: 34 out of 34 bytes
< HTTP/1.1 204 
HTTP/1.1 204 
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
* Connection #0 to host 127.0.0.1 left intact
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> PUT /api/arches/14/graph HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:text/uri-list
> Content-Length: 34
> 
* upload completely sent off: 34 out of 34 bytes
< HTTP/1.1 204 
HTTP/1.1 204 
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
* Connection #0 to host 127.0.0.1 left intact
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> PUT /api/arches/15/graph HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:text/uri-list
> Content-Length: 34
> 
* upload completely sent off: 34 out of 34 bytes
< HTTP/1.1 204 
HTTP/1.1 204 
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
* Connection #0 to host 127.0.0.1 left intact
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> PUT /api/arches/16/graph HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:text/uri-list
> Content-Length: 34
> 
* upload completely sent off: 34 out of 34 bytes
< HTTP/1.1 204 
HTTP/1.1 204 
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
* Connection #0 to host 127.0.0.1 left intact
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> PUT /api/arches/17/graph HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:text/uri-list
> Content-Length: 34
> 
* upload completely sent off: 34 out of 34 bytes
< HTTP/1.1 204 
HTTP/1.1 204 
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
* Connection #0 to host 127.0.0.1 left intact
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> PUT /api/arches/18/graph HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:text/uri-list
> Content-Length: 34
> 
* upload completely sent off: 34 out of 34 bytes
< HTTP/1.1 204 
HTTP/1.1 204 
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
* Connection #0 to host 127.0.0.1 left intact
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> PUT /api/arches/19/graph HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:text/uri-list
> Content-Length: 34
> 
* upload completely sent off: 34 out of 34 bytes
< HTTP/1.1 204 
HTTP/1.1 204 
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
* Connection #0 to host 127.0.0.1 left intact
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> PUT /api/arches/20/graph HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:text/uri-list
> Content-Length: 34
> 
* upload completely sent off: 34 out of 34 bytes
< HTTP/1.1 204 
HTTP/1.1 204 
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
* Connection #0 to host 127.0.0.1 left intact
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> GET /api/graphs HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> 
< HTTP/1.1 200 
HTTP/1.1 200 
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
{
  "_embedded" : {
    "graphs" : [ {
      "label" : "G1",
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/graphs/1"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/graphs/1"
        },
        "arches" : {
          "href" : "http://127.0.0.1:9090/api/graphs/1/arches"
        },
        "vertexes" : {
          "href" : "http://127.0.0.1:9090/api/graphs/1/vertexes"
        }
      }
    } ]
  },
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/graphs"
    },
    "profile" : {
      "href" : "http://127.0.0.1:9090/api/profile/graphs"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> GET /api/graphs/1/vertexes HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> 
< HTTP/1.1 200 
HTTP/1.1 200 
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
{
  "_embedded" : {
    "vertexes" : [ {
      "label" : "VS",
      "index" : 0,
      "source" : true,
      "sink" : false,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/2"
        },
        "vertex" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/2"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/2/graph"
        }
      }
    }, {
      "label" : "V1",
      "index" : 1,
      "source" : false,
      "sink" : false,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/3"
        },
        "vertex" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/3"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/3/graph"
        }
      }
    }, {
      "label" : "V2",
      "index" : 2,
      "source" : false,
      "sink" : false,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/4"
        },
        "vertex" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/4"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/4/graph"
        }
      }
    }, {
      "label" : "V3",
      "index" : 3,
      "source" : false,
      "sink" : false,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/5"
        },
        "vertex" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/5"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/5/graph"
        }
      }
    }, {
      "label" : "V4",
      "index" : 4,
      "source" : false,
      "sink" : false,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/6"
        },
        "vertex" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/6"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/6/graph"
        }
      }
    }, {
      "label" : "V5",
      "index" : 5,
      "source" : false,
      "sink" : false,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/7"
        },
        "vertex" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/7"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/7/graph"
        }
      }
    }, {
      "label" : "VT",
      "index" : 6,
      "source" : false,
      "sink" : true,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/8"
        },
        "vertex" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/8"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/vertexes/8/graph"
        }
      }
    } ]
  },
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/graphs/1/vertexes"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> GET /api/graphs/1/arches HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> 
< HTTP/1.1 200 
HTTP/1.1 200 
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Sat, 03 Nov 2018 05:49:08 GMT
Date: Sat, 03 Nov 2018 05:49:08 GMT

< 
{
  "_embedded" : {
    "arches" : [ {
      "label" : "A1",
      "previous" : 0,
      "following" : 1,
      "flow" : 0.0,
      "capacity" : 3.0,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/arches/9"
        },
        "arch" : {
          "href" : "http://127.0.0.1:9090/api/arches/9"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/arches/9/graph"
        }
      }
    }, {
      "label" : "A2",
      "previous" : 0,
      "following" : 2,
      "flow" : 0.0,
      "capacity" : 3.0,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/arches/10"
        },
        "arch" : {
          "href" : "http://127.0.0.1:9090/api/arches/10"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/arches/10/graph"
        }
      }
    }, {
      "label" : "A3",
      "previous" : 0,
      "following" : 3,
      "flow" : 0.0,
      "capacity" : 5.0,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/arches/11"
        },
        "arch" : {
          "href" : "http://127.0.0.1:9090/api/arches/11"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/arches/11/graph"
        }
      }
    }, {
      "label" : "A4",
      "previous" : 1,
      "following" : 4,
      "flow" : 0.0,
      "capacity" : 3.0,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/arches/12"
        },
        "arch" : {
          "href" : "http://127.0.0.1:9090/api/arches/12"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/arches/12/graph"
        }
      }
    }, {
      "label" : "A5",
      "previous" : 1,
      "following" : 3,
      "flow" : 0.0,
      "capacity" : 4.0,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/arches/13"
        },
        "arch" : {
          "href" : "http://127.0.0.1:9090/api/arches/13"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/arches/13/graph"
        }
      }
    }, {
      "label" : "A6",
      "previous" : 2,
      "following" : 5,
      "flow" : 0.0,
      "capacity" : 2.0,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/arches/14"
        },
        "arch" : {
          "href" : "http://127.0.0.1:9090/api/arches/14"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/arches/14/graph"
        }
      }
    }, {
      "label" : "A7",
      "previous" : 2,
      "following" : 3,
      "flow" : 0.0,
      "capacity" : 2.0,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/arches/15"
        },
        "arch" : {
          "href" : "http://127.0.0.1:9090/api/arches/15"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/arches/15/graph"
        }
      }
    }, {
      "label" : "A8",
      "previous" : 3,
      "following" : 4,
      "flow" : 0.0,
      "capacity" : 2.0,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/arches/16"
        },
        "arch" : {
          "href" : "http://127.0.0.1:9090/api/arches/16"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/arches/16/graph"
        }
      }
    }, {
      "label" : "A9",
      "previous" : 3,
      "following" : 5,
      "flow" : 0.0,
      "capacity" : 4.0,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/arches/17"
        },
        "arch" : {
          "href" : "http://127.0.0.1:9090/api/arches/17"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/arches/17/graph"
        }
      }
    }, {
      "label" : "A10",
      "previous" : 3,
      "following" : 6,
      "flow" : 0.0,
      "capacity" : 1.0,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/arches/18"
        },
        "arch" : {
          "href" : "http://127.0.0.1:9090/api/arches/18"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/arches/18/graph"
        }
      }
    }, {
      "label" : "A11",
      "previous" : 4,
      "following" : 6,
      "flow" : 0.0,
      "capacity" : 4.0,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/arches/19"
        },
        "arch" : {
          "href" : "http://127.0.0.1:9090/api/arches/19"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/arches/19/graph"
        }
      }
    }, {
      "label" : "A12",
      "previous" : 5,
      "following" : 6,
      "flow" : 0.0,
      "capacity" : 4.0,
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/arches/20"
        },
        "arch" : {
          "href" : "http://127.0.0.1:9090/api/arches/20"
        },
        "graph" : {
          "href" : "http://127.0.0.1:9090/api/arches/20/graph"
        }
      }
    } ]
  },
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/graphs/1/arches"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}

```
## After which I try with the following command:
```

```
