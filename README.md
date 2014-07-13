InEvent for GovHack 2014
========================

The Pitch
---------

inEvent is for the eager adventure seekers looking for events in Melbourne now. Leveraging Events Victoria, Eventbrite data and smartphone GPS technology, nearby events are presented in quick succession and those that tickle the user's fancy are shortlisted with the flick of a finger. Events data is complemented with Public Transport Victoria train, tram and bus stop locations near the event venue so commuting is a breeze. inEvent is uses unique combination of serendipity and geolocation information to make the Melbourne's huge number of events pleasantly surprising -- discover Melbourne!

Business case - a SWOT analysis
-------------------------------
Strengths

The publicly available Events Victoria dataset contains over 1500 unique events, where including recurring events or events that span multiple days this expands to over 58,000 entries. Including other public APIs, such as Eventbrite, the aggregate dataset may expand substantially. inEvent recognises that a user could not feasibly process this extensive set of data, so inEvent leverages existing categories available in the datasets, intelligent event selection, weighted based on location and time of day, to improve the chances of an appealing event being presented to the user. This is achieved all through a mobile interface; an exceedingly portable and popular mode of accessing location-specific information in an ad-hoc manner.

Weaknesses

The Events Victoria dataset is overly specific in terms of assigned categories/tags (~70 unique categories), which do not lend themselves to efficient user-specified search refinement on a mobile device.
Mitigation inEvent has attempted to merge several specific categories into broader terms which allow the user to target their preferences without being overwhelmed by category choice.

Events Victoria data do not contain event start and end times, which limit the ability to provide time-sensitive events. For example, it would not be possible to provide all events beginning within the hour.
Mitigation Search weighting in inEvent progressively shifts to events occurring the following day as the day progresses. While not removing the aforementioned issue, the likelihood of events that have not passed is reduced without a significant loss of “immediacy”; a key selling-point of this application.

Events Victoria data do not contain specific pricing information; events are only identified as free or not free.
Mitigation inEvent has provided preliminary code to support pricing information in a future version, however in the time being the free or not-free data is used.

Opportunities
Future development

inEvent demonstrates the ability to aggregate data from several sources including multiple events databases and public transport information. By developing this extensible framework, inEvent has the potential to incorporate additional feeds and social data to become the premier source of events data targeting last-minute or spontaneous planners.

inEvent may be developed in terms of improving dynamic data extraction (e.g. Bayesian text analysis, natural language processing) in order to convert non-machine-readable data into an indexable form.
Behavioural metrics may be leveraged in order to improve intelligent event selection based on heuristics associated with user profiles.
Incorporating social networks (e.g. twitter) will help establish a user base, which will increase word-of-mouth promotion and potentially provide useful social metrics
Monetisation

Syndication with events data providers, promoters and organisers provides an opportunity to support application development and maintenance (e.g. hosting costs). Future monetisation strategies may surround operating directly with events organisers and venues and providing booking / ticket payment services.

Threats

Commercial events data providers, while providing APIs to access their data, are similarly in the business of promoting events directly to their users, often with mobile frontends. To this end, while syndication with data providers may provide a source of income, sole reliance on these data providers may limit the scope for future development. With this in mind, inEvent has the potential to become an authoritative events data provider, directly liaising with event organisers and venues to provide listings.

Installation
============

Requirements
------------

* Public Transport Victoria API key
* Eventbrite OAuth token
* maven3 (earlier versions may have problems)
* ElasticSearch
* Java 1.8
* Linux distro (tested on Ubuntu 14.04)

Steps to install
----------------
1. Install dependencies
2. Clone the repository
3. Edit the build-inivent-from-scratch script to include the appropriate API keys
4. Run build script (build-inivent-from-scratch) inside repository
5. Ensure ElasticSearch is started
6. Run populate-inivent (consider running under cron)
7. Start the server (start-inivent) under nohup (keeps it running once the interactive session ends), or consider making an upstart conf file
