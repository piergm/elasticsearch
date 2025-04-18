---
mapped_pages:
  - https://www.elastic.co/guide/en/elasticsearch/reference/current/search-profile.html
applies_to:
  stack: all
---

# Profile search requests

::::{warning}
The Profile API is a debugging tool and adds significant overhead to search execution.
::::

The Profile API provides detailed timing information about the execution of individual components in a search request.
It gives the user insight into how search requests are executed at a low level so that the user can understand why certain requests are slow, and take steps to improve them.

Note that the Profile API, [amongst other things](search-profile.md#profile-limitations), doesn’t measure network latency, time the requests spend in queues, or time spent merging shard responses on the coordinating node.

The output from the Profile API is **very** verbose, especially for complicated requests executed across many shards. Pretty-printing the response is recommended to help understand the output.

::::{admonition} New API reference
For the most up-to-date API details, refer to [Search APIs](https://www.elastic.co/docs/api/doc/elasticsearch/group/endpoint-search).
::::

Any `_search` request can be profiled by adding a top-level `profile` parameter:

```console
GET /my-index-000001/_search
{
  "profile": true, <1>
  "query" : {
    "match" : { "message" : "GET /search" }
  }
}
```

1. Setting the top-level `profile` parameter to `true` will enable profiling for the search.


The API returns the following result:

```console-result
{
  "took": 25,
  "timed_out": false,
  "_shards": {
    "total": 1,
    "successful": 1,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": {
      "value": 5,
      "relation": "eq"
    },
    "max_score": 0.17402273,
    "hits": [...] <1>
  },
  "profile": {
    "shards": [
      {
        "id": "[q2aE02wS1R8qQFnYu6vDVQ][my-index-000001][0]",
        "node_id": "q2aE02wS1R8qQFnYu6vDVQ",
        "shard_id": 0,
        "index": "my-index-000001",
        "cluster": "(local)",
        "searches": [
          {
            "query": [
              {
                "type": "BooleanQuery",
                "description": "message:get message:search",
                "time_in_nanos" : 11972972,
                "breakdown" : {
                  "set_min_competitive_score_count": 0,
                  "match_count": 5,
                  "shallow_advance_count": 0,
                  "set_min_competitive_score": 0,
                  "next_doc": 39022,
                  "match": 4456,
                  "next_doc_count": 5,
                  "score_count": 5,
                  "compute_max_score_count": 0,
                  "compute_max_score": 0,
                  "advance": 84525,
                  "advance_count": 1,
                  "score": 37779,
                  "build_scorer_count": 2,
                  "create_weight": 4694895,
                  "shallow_advance": 0,
                  "create_weight_count": 1,
                  "build_scorer": 7112295,
                  "count_weight": 0,
                  "count_weight_count": 0
                },
                "children": [
                  {
                    "type": "TermQuery",
                    "description": "message:get",
                    "time_in_nanos": 3801935,
                    "breakdown": {
                      "set_min_competitive_score_count": 0,
                      "match_count": 0,
                      "shallow_advance_count": 3,
                      "set_min_competitive_score": 0,
                      "next_doc": 0,
                      "match": 0,
                      "next_doc_count": 0,
                      "score_count": 5,
                      "compute_max_score_count": 3,
                      "compute_max_score": 32487,
                      "advance": 5749,
                      "advance_count": 6,
                      "score": 16219,
                      "build_scorer_count": 3,
                      "create_weight": 2382719,
                      "shallow_advance": 9754,
                      "create_weight_count": 1,
                      "build_scorer": 1355007,
                      "count_weight": 0,
                      "count_weight_count": 0
                    }
                  },
                  {
                    "type": "TermQuery",
                    "description": "message:search",
                    "time_in_nanos": 205654,
                    "breakdown": {
                      "set_min_competitive_score_count": 0,
                      "match_count": 0,
                      "shallow_advance_count": 3,
                      "set_min_competitive_score": 0,
                      "next_doc": 0,
                      "match": 0,
                      "next_doc_count": 0,
                      "score_count": 5,
                      "compute_max_score_count": 3,
                      "compute_max_score": 6678,
                      "advance": 12733,
                      "advance_count": 6,
                      "score": 6627,
                      "build_scorer_count": 3,
                      "create_weight": 130951,
                      "shallow_advance": 2512,
                      "create_weight_count": 1,
                      "build_scorer": 46153,
                      "count_weight": 0,
                      "count_weight_count": 0
                    }
                  }
                ]
              }
            ],
            "rewrite_time": 451233,
            "collector": [
              {
                "name": "QueryPhaseCollector",
                "reason": "search_query_phase",
                "time_in_nanos": 775274,
                "children" : [
                  {
                    "name": "SimpleTopScoreDocCollector",
                    "reason": "search_top_hits",
                    "time_in_nanos": 775274
                  }
                ]
              }
            ]
          }
        ],
        "aggregations": [],
        "fetch": {
          "type": "fetch",
          "description": "",
          "time_in_nanos": 660555,
          "breakdown": {
            "next_reader": 7292,
            "next_reader_count": 1,
            "load_stored_fields": 299325,
            "load_stored_fields_count": 5,
            "load_source": 3863,
            "load_source_count": 5
          },
          "debug": {
            "stored_fields": ["_id", "_routing", "_source"]
          },
          "children": [
            {
              "type" : "FetchFieldsPhase",
              "description" : "",
              "time_in_nanos" : 238762,
              "breakdown" : {
                "process_count" : 5,
                "process" : 227914,
                "next_reader" : 10848,
                "next_reader_count" : 1
              }
            },
            {
              "type": "FetchSourcePhase",
              "description": "",
              "time_in_nanos": 20443,
              "breakdown": {
                "next_reader": 745,
                "next_reader_count": 1,
                "process": 19698,
                "process_count": 5
              },
              "debug": {
                "fast_path": 5
              }
            },
            {
              "type": "StoredFieldsPhase",
              "description": "",
              "time_in_nanos": 5310,
              "breakdown": {
                "next_reader": 745,
                "next_reader_count": 1,
                "process": 4445,
                "process_count": 5
              }
            }
          ]
        }
      }
    ]
  }
}
```

1. Search results are returned, but were omitted here for brevity.


Even for a simple query, the response is relatively complicated. Let’s break it down piece-by-piece before moving to more complex examples.

The overall structure of the profile response is as follows:

```console-result
{
   "profile": {
        "shards": [
           {
              "id": "[q2aE02wS1R8qQFnYu6vDVQ][my-index-000001][0]",  <1>
              "node_id": "q2aE02wS1R8qQFnYu6vDVQ",
              "shard_id": 0,
              "index": "my-index-000001",
              "cluster": "(local)",             <2>
              "searches": [
                 {
                    "query": [...],             <3>
                    "rewrite_time": 51443,      <4>
                    "collector": [...]          <5>
                 }
              ],
              "aggregations": [...],            <6>
              "fetch": {...}                    <7>
           }
        ]
     }
}
```

1. A profile is returned for each shard that participated in the response, and is identified by a unique ID.
2. If the query was run on the local cluster, the cluster name is left out of the composite id and is marked "(local)" here. For a profile running on a remote_cluster using cross-cluster search, the "id" value would be something like `[q2aE02wS1R8qQFnYu6vDVQ][remote1:my-index-000001][0]` and the "cluster" value would be `remote1`.
3. Query timings and other debugging information.
4. The cumulative rewrite time.
5. Names and invocation timings for each collector.
6. Aggregation timings, invocation counts, and debug information.
7. Fetch timing and debug information.


Because a search request may be executed against one or more shards in an index, and a search may cover one or more indices, the top level element in the profile response is an array of `shard` objects. Each shard object lists its `id` which uniquely identifies the shard. The ID’s format is `[nodeID][clusterName:indexName][shardID]`. If the search is run against the local cluster then the clusterName is not added and the format is `[nodeID][indexName][shardID]`.

The profile itself may consist of one or more "searches", where a search is a query executed against the underlying Lucene index. Most search requests submitted by the user will only execute a single `search` against the Lucene index. But occasionally multiple searches will be executed, such as including a global aggregation (which needs to execute a secondary "match_all" query for the global context).

Inside each `search` object there will be two arrays of profiled information: a `query` array and a `collector` array. Alongside the `search` object is an `aggregations` object that contains the profile information for the aggregations. In the future, more sections may be added, such as `suggest`, `highlight`, etc.

There will also be a `rewrite` metric showing the total time spent rewriting the query (in nanoseconds).

::::{note}
As with other statistics apis, the Profile API supports human readable outputs. This can be turned on by adding `?human=true` to the query string. In this case, the output contains the additional `time` field containing rounded, human readable timing information (e.g. `"time": "391,9ms"`, `"time": "123.3micros"`).
::::

## Profiling queries [profiling-queries]

::::{note}
The details provided by the Profile API directly expose Lucene class names and concepts, which means that complete interpretation of the results require fairly advanced knowledge of Lucene. This page attempts to give a crash-course in how Lucene executes queries so that you can use the Profile API to successfully diagnose and debug queries, but it is only an overview. For complete understanding, please refer to Lucene’s documentation and, in places, the code.

With that said, a complete understanding is often not required to fix a slow query. It is usually sufficient to see that a particular component of a query is slow, and not necessarily understand why the `advance` phase of that query is the cause, for example.

::::


### Query section [query-section]

The `query` section contains detailed timing of the query tree executed by Lucene on a particular shard. The overall structure of this query tree will resemble your original Elasticsearch query, but may be slightly (or sometimes very) different. It will also use similar but not always identical naming. Using our previous `match` query example, let’s analyze the `query` section:

```console-result
"query": [
    {
       "type": "BooleanQuery",
       "description": "message:get message:search",
       "time_in_nanos": "11972972",
       "breakdown": {...},               <1>
       "children": [
          {
             "type": "TermQuery",
             "description": "message:get",
             "time_in_nanos": "3801935",
             "breakdown": {...}
          },
          {
             "type": "TermQuery",
             "description": "message:search",
             "time_in_nanos": "205654",
             "breakdown": {...}
          }
       ]
    }
]
```

1. The breakdown timings are omitted for simplicity.


Based on the profile structure, we can see that our `match` query was rewritten by Lucene into a BooleanQuery with two clauses (both holding a TermQuery). The `type` field displays the Lucene class name, and often aligns with the equivalent name in Elasticsearch. The `description` field displays the Lucene explanation text for the query, and is made available to help differentiating between parts of your query (e.g. both `message:get` and `message:search` are TermQuery’s and would appear identical otherwise.

The `time_in_nanos` field shows that this query took ~11.9ms for the entire BooleanQuery to execute. The recorded time is inclusive of all children.

The `breakdown` field will give detailed stats about how the time was spent, we’ll look at that in a moment. Finally, the `children` array lists any sub-queries that may be present. Because we searched for two values ("get search"), our BooleanQuery holds two children TermQueries. They have identical information (type, time, breakdown, etc). Children are allowed to have their own children.


### Timing breakdown [_timing_breakdown]

The `breakdown` component lists detailed timing statistics about low-level Lucene execution:

```console-result
"breakdown": {
  "set_min_competitive_score_count": 0,
  "match_count": 5,
  "shallow_advance_count": 0,
  "set_min_competitive_score": 0,
  "next_doc": 39022,
  "match": 4456,
  "next_doc_count": 5,
  "score_count": 5,
  "compute_max_score_count": 0,
  "compute_max_score": 0,
  "advance": 84525,
  "advance_count": 1,
  "score": 37779,
  "build_scorer_count": 2,
  "create_weight": 4694895,
  "shallow_advance": 0,
  "create_weight_count": 1,
  "build_scorer": 7112295,
  "count_weight": 0,
  "count_weight_count": 0
}
```

Timings are listed in wall-clock nanoseconds and are not normalized at all. All caveats about the overall `time_in_nanos` apply here. The intention of the breakdown is to give you a feel for A) what machinery in Lucene is actually eating time, and B) the magnitude of differences in times between the various components. Like the overall time, the breakdown is inclusive of all children times.

The meaning of the stats are as follows:


#### All parameters [_all_parameters]

`create_weight`
:   A Query in Lucene must be capable of reuse across multiple IndexSearchers (think of it as the engine that executes a search against a specific Lucene Index). This puts Lucene in a tricky spot, since many queries need to accumulate temporary state/statistics associated with the index it is being used against, but the Query contract mandates that it must be immutable. <br> <br> To get around this, Lucene asks each query to generate a Weight object which acts as a temporary context object to hold state associated with this particular (IndexSearcher, Query) tuple. The `weight` metric shows how long this process takes

`build_scorer`
:   This parameter shows how long it takes to build a Scorer for the query. A Scorer is the mechanism that iterates over matching documents and generates a score per-document (e.g. how well does "foo" match the document?). Note, this records the time required to generate the Scorer object, not actually score the documents. Some queries have faster or slower initialization of the Scorer, depending on optimizations, complexity, etc. This may also show timing associated with caching, if enabled and/or applicable for the query

`next_doc`
:   The Lucene method `next_doc` returns Doc ID of the next document matching the query. This statistic shows the time it takes to determine which document is the next match, a process that varies considerably depending on the nature of the query. Next_doc is a specialized form of advance() which is more convenient for many queries in Lucene. It is equivalent to advance(docId() + 1)

`advance`
:   `advance` is the "lower level" version of next_doc: it serves the same purpose of finding the next matching doc, but requires the calling query to perform extra tasks such as identifying and moving past skips, etc. However,  not all queries can use next_doc, so `advance` is also timed for those queries. <br> <br> Conjunctions (e.g. `must` clauses in a Boolean) are typical consumers of `advance`

`match`
:   Some queries, such as phrase queries, match documents using a "two-phase" process. First, the document is "approximately" matched, and if it matches approximately, it is checked a second time with a more rigorous (and expensive) process. The second phase verification is what the `match` statistic measures. <br> <br> For example, a phrase query first checks a document approximately by ensuring all terms in the phrase are present in the doc. If all the terms are present, it then executes the second phase verification to ensure the terms are in-order to form the phrase, which is relatively more expensive than just checking for presence of the terms. <br> <br> Because this two-phase process is only used by a handful of queries, the `match` statistic is often zero

`score`
:   This records the time taken to score a particular document via its Scorer

`*_count`
:   Records the number of invocations of the particular method. For example, `"next_doc_count": 2,` means the `nextDoc()` method was called on two different documents. This can be used to help judge how selective queries are, by comparing counts between different query components.


### Collectors section [collectors-section]

The Collectors portion of the response shows high-level execution details. Lucene works by defining a "Collector" which is responsible for coordinating the traversal, scoring, and collection of matching documents. Collectors are also how a single query can record aggregation results, execute unscoped "global" queries, execute post-query filters, etc.

Looking at the previous example:

```console-result
"collector": [
  {
    "name": "QueryPhaseCollector",
    "reason": "search_query_phase",
    "time_in_nanos": 775274,
    "children" : [
      {
        "name": "SimpleTopScoreDocCollector",
        "reason": "search_top_hits",
        "time_in_nanos": 775274
      }
    ]
  }
]
```

We see a top-level collector named `QueryPhaseCollector` which holds a child `SimpleTopScoreDocCollector`. `SimpleTopScoreDocCollector` is the  default "scoring and sorting" `Collector` used by {{es}}. The `reason` field attempts to give a plain English description of the class name. The `time_in_nanos` is similar to the time in the Query tree: a wall-clock time inclusive of all children. Similarly, `children` lists all sub-collectors. When aggregations are requested, the `QueryPhaseCollector` will hold an additional child collector with reason `aggregation` that is the one performing aggregations.

It should be noted that Collector times are **independent** from the Query times. They are calculated, combined, and normalized independently! Due to the nature of Lucene’s execution, it is impossible to "merge" the times from the Collectors into the Query section, so they are displayed in separate portions.

For reference, the various collector reasons are:

`search_top_hits`
:   A collector that scores and sorts documents. This is the most common collector and will be seen in most simple searches

`search_count`
:   A collector that only counts the number of documents that match the query, but does not fetch the source. This is seen when `size: 0` is specified

`search_query_phase`
:   A collector that incorporates collecting top hits as well aggregations as part of the query phase. It supports terminating the search execution after `n` matching documents have been found (when `terminate_after` is specified), as well as only returning matching documents that have a score greater than `n` (when `min_score` is provided). Additionally, it is able to filter matching top hits based on the provided `post_filter`.

`search_timeout`
:   A collector that halts execution after a specified period of time. This is seen when a `timeout` top-level parameter has been specified.

`aggregation`
:   A collector that Elasticsearch uses to run aggregations against the query scope. A single `aggregation` collector is used to collect documents for **all** aggregations, so you will see a list of aggregations in the name rather.

`global_aggregation`
:   A collector that executes an aggregation against the global query scope, rather than the specified query. Because the global scope is necessarily different from the executed query, it must execute its own match_all query (which you will see added to the Query section) to collect your entire dataset


### Rewrite section [rewrite-section]

All queries in Lucene undergo a "rewriting" process. A query (and its sub-queries) may be rewritten one or more times, and the process continues until the query stops changing. This process allows Lucene to perform optimizations, such as removing redundant clauses, replacing one query for a more efficient execution path, etc. For example a Boolean → Boolean → TermQuery can be rewritten to a TermQuery, because all the Booleans are unnecessary in this case.

The rewriting process is complex and difficult to display, since queries can change drastically. Rather than showing the intermediate results, the total rewrite time is simply displayed as a value (in nanoseconds). This value is cumulative and contains the total time for all queries being rewritten.


### A more complex example [_a_more_complex_example]

To demonstrate a slightly more complex query and the associated results, we can profile the following query:

```console
GET /my-index-000001/_search
{
  "profile": true,
  "query": {
    "term": {
      "user.id": {
        "value": "elkbee"
      }
    }
  },
  "aggs": {
    "my_scoped_agg": {
      "terms": {
        "field": "http.response.status_code"
      }
    },
    "my_global_agg": {
      "global": {},
      "aggs": {
        "my_level_agg": {
          "terms": {
            "field": "http.response.status_code"
          }
        }
      }
    }
  },
  "post_filter": {
    "match": {
      "message": "search"
    }
  }
}
```

This example has:

* A query
* A scoped aggregation
* A global aggregation
* A post_filter

The API returns the following result:

```console-result
{
  ...
  "profile": {
    "shards": [
      {
        "id": "[P6xvulHtQRWuD4YnubWb7A][my-index-000001][0]",
        "node_id": "P6xvulHtQRWuD4YnubWb7A",
        "shard_id": 0,
        "index": "my-index-000001",
        "cluster": "(local)",
        "searches": [
          {
            "query": [
              {
                "type": "TermQuery",
                "description": "message:search",
                "time_in_nanos": 141618,
                "breakdown": {
                  "set_min_competitive_score_count": 0,
                  "match_count": 0,
                  "shallow_advance_count": 0,
                  "set_min_competitive_score": 0,
                  "next_doc": 0,
                  "match": 0,
                  "next_doc_count": 0,
                  "score_count": 0,
                  "compute_max_score_count": 0,
                  "compute_max_score": 0,
                  "advance": 3942,
                  "advance_count": 4,
                  "count_weight_count": 0,
                  "score": 0,
                  "build_scorer_count": 2,
                  "create_weight": 38380,
                  "shallow_advance": 0,
                  "count_weight": 0,
                  "create_weight_count": 1,
                  "build_scorer": 99296
                }
              },
              {
                "type": "TermQuery",
                "description": "user.id:elkbee",
                "time_in_nanos": 163081,
                "breakdown": {
                  "set_min_competitive_score_count": 0,
                  "match_count": 0,
                  "shallow_advance_count": 0,
                  "set_min_competitive_score": 0,
                  "next_doc": 2447,
                  "match": 0,
                  "next_doc_count": 4,
                  "score_count": 4,
                  "compute_max_score_count": 0,
                  "compute_max_score": 0,
                  "advance": 3552,
                  "advance_count": 1,
                  "score": 5027,
                  "count_weight_count": 0,
                  "build_scorer_count": 2,
                  "create_weight": 107840,
                  "shallow_advance": 0,
                  "count_weight": 0,
                  "create_weight_count": 1,
                  "build_scorer": 44215
                }
              }
            ],
            "rewrite_time": 4769,
            "collector": [
              {
                "name": "QueryPhaseCollector",
                "reason": "search_query_phase",
                "time_in_nanos": 1945072,
                "children": [
                  {
                    "name": "SimpleTopScoreDocCollector",
                    "reason": "search_top_hits",
                    "time_in_nanos": 22577
                  },
                  {
                    "name": "AggregatorCollector: [my_scoped_agg, my_global_agg]",
                    "reason": "aggregation",
                    "time_in_nanos": 867617
                  }
                ]
              }
            ]
          }
        ],
        "aggregations": [...], <1>
        "fetch": {...}
      }
    ]
  }
}
```

1. The `"aggregations"` portion has been omitted because it will be covered in the next section.


As you can see, the output is significantly more verbose than before. All the major portions of the query are represented:

1. The first `TermQuery` (user.id:elkbee) represents the main `term` query.
2. The second `TermQuery` (message:search) represents the `post_filter` query.

The Collector tree is fairly straightforward, showing how a single QueryPhaseCollector that holds the normal scoring SimpleTopScoreDocCollector used to collect top hits, as well as BucketCollectorWrapper to run all scoped aggregations.


### Understanding MultiTermQuery output [_understanding_multitermquery_output]

A special note needs to be made about the `MultiTermQuery` class of queries. This includes wildcards, regex, and fuzzy queries. These queries emit very verbose responses, and are not overly structured.

Essentially, these queries rewrite themselves on a per-segment basis. If you imagine the wildcard query `b*`, it technically can match any token that begins with the letter "b". It would be impossible to enumerate all possible combinations, so Lucene rewrites the query in context of the segment being evaluated, e.g., one segment may contain the tokens `[bar, baz]`, so the query rewrites to a BooleanQuery combination of "bar" and "baz". Another segment may only have the token `[bakery]`, so the query rewrites to a single TermQuery for "bakery".

Due to this dynamic, per-segment rewriting, the clean tree structure becomes distorted and no longer follows a clean "lineage" showing how one query rewrites into the next. At present time, all we can do is apologize, and suggest you collapse the details for that query’s children if it is too confusing. Luckily, all the timing statistics are correct, just not the physical layout in the response, so it is sufficient to just analyze the top-level MultiTermQuery and ignore its children if you find the details too tricky to interpret.

Hopefully this will be fixed in future iterations, but it is a tricky problem to solve and still in-progress. :)


### Profiling aggregations [profiling-aggregations]

#### Aggregations section [agg-section]

The `aggregations` section contains detailed timing of the aggregation tree executed by a particular shard. The overall structure of this aggregation tree will resemble your original {{es}} request. Let’s execute the previous query again and look at the aggregation profile this time:

```console
GET /my-index-000001/_search
{
  "profile": true,
  "query": {
    "term": {
      "user.id": {
        "value": "elkbee"
      }
    }
  },
  "aggs": {
    "my_scoped_agg": {
      "terms": {
        "field": "http.response.status_code"
      }
    },
    "my_global_agg": {
      "global": {},
      "aggs": {
        "my_level_agg": {
          "terms": {
            "field": "http.response.status_code"
          }
        }
      }
    }
  },
  "post_filter": {
    "match": {
      "message": "search"
    }
  }
}
```

This yields the following aggregation profile output:

```console-result
{
  "profile": {
    "shards": [
      {
        "aggregations": [
          {
            "type": "NumericTermsAggregator",
            "description": "my_scoped_agg",
            "time_in_nanos": 79294,
            "breakdown": {
              "reduce": 0,
              "build_aggregation": 30885,
              "build_aggregation_count": 1,
              "initialize": 2623,
              "initialize_count": 1,
              "reduce_count": 0,
              "collect": 45786,
              "collect_count": 4,
              "build_leaf_collector": 18211,
              "build_leaf_collector_count": 1,
              "post_collection": 929,
              "post_collection_count": 1
            },
            "debug": {
              "total_buckets": 1,
              "result_strategy": "long_terms",
              "built_buckets": 1
            }
          },
          {
            "type": "GlobalAggregator",
            "description": "my_global_agg",
            "time_in_nanos": 104325,
            "breakdown": {
              "reduce": 0,
              "build_aggregation": 22470,
              "build_aggregation_count": 1,
              "initialize": 12454,
              "initialize_count": 1,
              "reduce_count": 0,
              "collect": 69401,
              "collect_count": 4,
              "build_leaf_collector": 8150,
              "build_leaf_collector_count": 1,
              "post_collection": 1584,
              "post_collection_count": 1
            },
            "debug": {
              "built_buckets": 1
            },
            "children": [
              {
                "type": "NumericTermsAggregator",
                "description": "my_level_agg",
                "time_in_nanos": 76876,
                "breakdown": {
                  "reduce": 0,
                  "build_aggregation": 13824,
                  "build_aggregation_count": 1,
                  "initialize": 1441,
                  "initialize_count": 1,
                  "reduce_count": 0,
                  "collect": 61611,
                  "collect_count": 4,
                  "build_leaf_collector": 5564,
                  "build_leaf_collector_count": 1,
                  "post_collection": 471,
                  "post_collection_count": 1
                },
                "debug": {
                  "total_buckets": 1,
                  "result_strategy": "long_terms",
                  "built_buckets": 1
                }
              }
            ]
          }
        ]
      }
    ]
  }
}
```

From the profile structure we can see that the `my_scoped_agg` is internally being run as a `NumericTermsAggregator` (because the field it is aggregating, `http.response.status_code`, is a numeric field). At the same level, we see a `GlobalAggregator` which comes from `my_global_agg`. That aggregation then has a child `NumericTermsAggregator` which comes from the second term’s aggregation on `http.response.status_code`.

The `time_in_nanos` field shows the time executed by each aggregation, and is inclusive of all children. While the overall time is useful, the `breakdown` field will give detailed stats about how the time was spent.

Some aggregations may return expert `debug` information that describe features of the underlying execution of the aggregation that are 'useful for folks that hack on aggregations but that we don’t expect to be otherwise useful. They can vary wildly between versions, aggregations, and aggregation execution strategies.

### Timing breakdown [_timing_breakdown_2]

The `breakdown` component lists detailed statistics about low-level execution:

```js
"breakdown": {
  "reduce": 0,
  "build_aggregation": 30885,
  "build_aggregation_count": 1,
  "initialize": 2623,
  "initialize_count": 1,
  "reduce_count": 0,
  "collect": 45786,
  "collect_count": 4,
  "build_leaf_collector": 18211,
  "build_leaf_collector_count": 1
}
```

Each property in the `breakdown` component corresponds to an internal method for the aggregation. For example, the `build_leaf_collector` property measures nanoseconds spent running the aggregation’s `getLeafCollector()` method. Properties ending in `_count` record the number of invocations of the particular method. For example, `"collect_count": 2` means the aggregation called the `collect()` on two different documents. The `reduce` property is reserved for future use and always returns `0`.

Timings are listed in wall-clock nanoseconds and are not normalized at all. All caveats about the overall `time` apply here. The intention of the breakdown is to give you a feel for A) what machinery in {{es}} is actually eating time, and B) the magnitude of differences in times between the various components. Like the overall time, the breakdown is inclusive of all children times.


### Profiling fetch [profiling-fetch]

All shards that fetched documents will have a `fetch` section in the profile. Let’s execute a small search and have a look at the fetch profile:

```console
GET /my-index-000001/_search?filter_path=profile.shards.fetch
{
  "profile": true,
  "query": {
    "term": {
      "user.id": {
        "value": "elkbee"
      }
    }
  }
}
```

And here is the fetch profile:

```console-result
{
  "profile": {
    "shards": [
      {
        "fetch": {
          "type": "fetch",
          "description": "",
          "time_in_nanos": 660555,
          "breakdown": {
            "next_reader": 7292,
            "next_reader_count": 1,
            "load_stored_fields": 299325,
            "load_stored_fields_count": 5,
            "load_source": 3863,
            "load_source_count": 5
          },
          "debug": {
            "stored_fields": ["_id", "_routing", "_source"]
          },
          "children": [
            {
              "type" : "FetchFieldsPhase",
              "description" : "",
              "time_in_nanos" : 238762,
              "breakdown" : {
                "process_count" : 5,
                "process" : 227914,
                "next_reader" : 10848,
                "next_reader_count" : 1
              }
            },
            {
              "type": "FetchSourcePhase",
              "description": "",
              "time_in_nanos": 20443,
              "breakdown": {
                "next_reader": 745,
                "next_reader_count": 1,
                "process": 19698,
                "process_count": 5
              },
              "debug": {
                "fast_path": 4
              }
            },
            {
              "type": "StoredFieldsPhase",
              "description": "",
              "time_in_nanos": 5310,
              "breakdown": {
                "next_reader": 745,
                "next_reader_count": 1,
                "process": 4445,
                "process_count": 5
              }
            }
          ]
        }
      }
    ]
  }
}
```

Since this is debugging information about the way that Elasticsearch executes the fetch it can change from request to request and version to version. Even patch versions may change the output here. That lack of consistency is what makes it useful for debugging.

Anyway! `time_in_nanos` measures the time total time of the fetch phase. The `breakdown` counts and times the our per-segment preparation in `next_reader` and the time taken loading stored fields in `load_stored_fields`. Debug contains miscellaneous non-timing information, specifically `stored_fields` lists the stored fields that fetch will have to load. If it is an empty list then fetch will entirely skip loading stored fields.

The `children` section lists the sub-phases that do the actual fetching work and the `breakdown` has counts and timings for the per-segment preparation in `next_reader` and the per document fetching in `process`.

::::{note}
We try hard to load all of the stored fields that we will need for the fetch up front. This tends to make the `_source` phase a couple of microseconds per hit. In that case the true cost of `_source` phase is hidden in the `load_stored_fields` component of the breakdown. It’s possible to entirely skip loading stored fields by setting `"_source": false, "stored_fields": ["_none_"]`.
::::



### Profiling DFS [profiling-dfs]

The DFS phase runs before the query phase to collect global information relevant to the query. It’s currently used in two cases:

1. When the `search_type` is set to [`dfs_query_then_fetch`](search-profile.md#profiling-dfs-statistics) and the index has multiple shards.
2. When the search request contains a [knn section](search-profile.md#profiling-knn-search).

Both of these cases can be profiled by setting `profile` to `true` as part of the search request.

#### Profiling DFS statistics [profiling-dfs-statistics]

When the `search_type` is set to `dfs_query_then_fetch` and the index has multiple shards, the dfs phase collects term statistics to improve the relevance of search results.

The following is an example of setting `profile` to `true` on a search that uses `dfs_query_then_fetch`:

Let’s first setup an index with multiple shards and index a pair of documents with different values on a `keyword` field.

$$$profile_dfs$$$

```console
PUT my-dfs-index
{
  "settings": {
    "number_of_shards": 2, <1>
    "number_of_replicas": 1
  },
  "mappings": {
      "properties": {
        "my-keyword": { "type": "keyword" }
      }
    }
}

POST my-dfs-index/_bulk?refresh=true
{ "index" : { "_id" : "1" } }
{ "my-keyword" : "a" }
{ "index" : { "_id" : "2" } }
{ "my-keyword" : "b" }
```

1. The `my-dfs-index` is created with multiple shards.


With an index setup, we can now profile the dfs phase of a search query. For this example we use a term query.

```console
GET /my-dfs-index/_search?search_type=dfs_query_then_fetch&pretty&size=0 <1>
{
  "profile": true, <2>
  "query": {
    "term": {
      "my-keyword": {
        "value": "a"
      }
    }
  }
}
```

1. The `search_type` url parameter is set to `dfs_query_then_fetch` to ensure the dfs phase is run.
2. The `profile` parameter is set to `true`.


In the response, we see a profile which includes a `dfs` section for each shard along with profile output for the rest of the search phases. One of the `dfs` sections for a shard looks like the following:

```console-result
"dfs" : {
    "statistics" : {
        "type" : "statistics",
        "description" : "collect term statistics",
        "time_in_nanos" : 236955,
        "breakdown" : {
            "term_statistics" : 4815,
            "collection_statistics" : 27081,
            "collection_statistics_count" : 1,
            "create_weight" : 153278,
            "term_statistics_count" : 1,
            "rewrite_count" : 0,
            "create_weight_count" : 1,
            "rewrite" : 0
        }
    }
}
```

In the `dfs.statistics` portion of this response we see a `time_in_nanos` which is the total time it took to collect term statistics for this shard along with a further breakdown of the individual parts.


#### Profiling kNN search [profiling-knn-search]

A k-nearest neighbor (kNN) search runs during the dfs phase.

The following is an example of setting `profile` to `true` on a search that has a `knn` section:

Let’s first setup an index with several dense vectors.

$$$profile_knn$$$

```console
PUT my-knn-index
{
  "mappings": {
    "properties": {
      "my-vector": {
        "type": "dense_vector",
        "dims": 3,
        "index": true,
        "similarity": "l2_norm"
      }
    }
  }
}

POST my-knn-index/_bulk?refresh=true
{ "index": { "_id": "1" } }
{ "my-vector": [1, 5, -20] }
{ "index": { "_id": "2" } }
{ "my-vector": [42, 8, -15] }
{ "index": { "_id": "3" } }
{ "my-vector": [15, 11, 23] }
```

With an index setup, we can now profile a kNN search query.

```console
POST my-knn-index/_search
{
  "profile": true, <1>
  "knn": {
    "field": "my-vector",
    "query_vector": [-5, 9, -12],
    "k": 3,
    "num_candidates": 100
  }
}
```

1. The `profile` parameter is set to `true`.


In the response, we see a profile which includes a `knn` section as part of the `dfs` section for each shard along with profile output for the rest of the search phases.

One of the `dfs.knn` sections for a shard looks like the following:

```js
"dfs" : {
    "knn" : [
        {
        "vector_operations_count" : 4,
        "query" : [
            {
                "type" : "DocAndScoreQuery",
                "description" : "DocAndScoreQuery[0,...][0.008961825,...],0.008961825",
                "time_in_nanos" : 444414,
                "breakdown" : {
                  "set_min_competitive_score_count" : 0,
                  "match_count" : 0,
                  "shallow_advance_count" : 0,
                  "set_min_competitive_score" : 0,
                  "next_doc" : 1688,
                  "match" : 0,
                  "next_doc_count" : 3,
                  "score_count" : 3,
                  "compute_max_score_count" : 0,
                  "compute_max_score" : 0,
                  "advance" : 4153,
                  "advance_count" : 1,
                  "score" : 2099,
                  "build_scorer_count" : 2,
                  "create_weight" : 128879,
                  "shallow_advance" : 0,
                  "create_weight_count" : 1,
                  "build_scorer" : 307595,
                  "count_weight": 0,
                  "count_weight_count": 0
                }
            }
        ],
        "rewrite_time" : 1275732,
        "collector" : [
            {
                "name" : "SimpleTopScoreDocCollector",
                "reason" : "search_top_hits",
                "time_in_nanos" : 17163
            }
        ]
    }   ]
}
```

In the `dfs.knn` portion of the response we can see the output the of timings for [query](search-profile.md#query-section), [rewrite](search-profile.md#rewrite-section), and [collector](search-profile.md#collectors-section). Unlike many other queries, kNN search does the bulk of the work during the query rewrite. This means `rewrite_time` represents the time spent on kNN search. The attribute `vector_operations_count` represents the overall count of vector operations performed during the kNN search.



### Profiling considerations [profiling-considerations]

Like any profiler, the Profile API introduces a non-negligible overhead to search execution. The act of instrumenting low-level method calls such as `collect`, `advance`, and `next_doc` can be fairly expensive, since these methods are called in tight loops. Therefore, profiling should not be enabled in production settings by default, and should not be compared against non-profiled query times. Profiling is just a diagnostic tool.

There are also cases where special Lucene optimizations are disabled, since they are not amenable to profiling. This could cause some queries to report larger relative times than their non-profiled counterparts, but in general should not have a drastic effect compared to other components in the profiled query.


### Limitations [profile-limitations]

* Profiling currently does not measure the network overhead.
* Profiling also does not account for time spent in the queue, merging shard responses on the coordinating node, or additional work such as building global ordinals (an internal data structure used to speed up search).
* Profiling statistics are currently not available for suggestions.
* Profiling of the reduce phase of aggregation is currently not available.
* The Profiler is instrumenting internals that can change from version to version. The resulting json should be considered mostly unstable, especially things in the `debug` section.
