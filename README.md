# webexTeamsStatus
This body of work was created during "The Rise of API Hackathon" on November 7th and 8th (2019).
This is a chatbot for webex teams that keeps track of a dynamic schedule

A dynamic schedule is a schedule that changes over time. Our schedule bot models continuous change by updating the predicted times for future time slots. As one slot begins to run over their expected duration, all slots after them are pushed back to accomodate this. As slots run under time, predicted times move forward.

# Commands
## :help
Displays each commans with a short description
## :startmeeting 
starts the dynamic schedule
## :addteam <teamname> 
Adds a named time block to the schedule
## :teamstart <teamname> 
Starts a team's actual time block
## :teamfinish <teamname> 
End a team's actual time block
## :agenda 
Presents the current predicted meeting agenda
