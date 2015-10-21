package com.gslab.query;

public interface Query_definition {
	String MOST_POPULAR_QUERY="select * from (select common_key,sum(watch_count_video) as watch_count,avg(viewed_percentage) as Average_watched from content_video cv,content_video_progress cvp where cv.video_pk=cvp.video_fk  group by common_key order by watch_count desc,Average_watched desc) as m where Average_watched>=:percentage limit 0,5;";
	String LEAST_POPULAR_QUERY="select * from (select common_key,sum(watch_count_video) as watch_count,avg(viewed_percentage) as Average_watched from content_video cv,content_video_progress cvp where cv.video_pk=cvp.video_fk  group by common_key order by watch_count asc,Average_watched asc) as m where Average_watched<=:percentage limit 0,5;";
    String POPULAR_BY_TIME_QUERY = "select common_key,sum(watch_count_video) as watch_count,avg(viewed_percentage) as Average_watched from content_video cv,content_video_progress cvp where cv.video_pk=cvp.video_fk and hour(watched_at) between :startTime and :endTime  group by common_key order by watch_count desc,Average_watched desc limit 0,5;";
    String MOST_OPTIMISED_VIDEO_LENGTH_QUERY="select sec_to_time(avg(time_to_sec(position))) as optimised_length from content_video_progress where viewed_percentage<>100;";
    String MOST_WATCHED_BY_PATIENT="select common_key,sum(watch_count_video) as watch_count from content_video cv,content_video_progress cvp where cv.video_pk=cvp.video_fk and user_id=:userId group by common_key order by watch_count desc;";
    String VIDEO_TRAFFIC_BY_HOUR="select hour(watched_at) time_hour,sum(watch_count_video) watch_count from content_video_progress group by hour(watched_at) order by time_hour asc;";
    String VIDEOS_IN_PROGRESS="select common_key,sum(watch_count_video) as watch_count,avg(viewed_percentage) as Average_watched from content_video cv,content_video_progress cvp where cv.video_pk=cvp.video_fk and viewed_percentage<>100  group by common_key;";
    String LANGUAGE_QUERY="select distinct language from content_video;";
    String POPULAR_LANGUAGE_QUERY="select * from (select common_key,sum(watch_count_video) as watch_count,avg(viewed_percentage) as Average_watched from content_video cv,content_video_progress cvp where cv.video_pk=cvp.video_fk and language=:language  group by common_key order by watch_count desc,Average_watched desc) as m limit 0,5;";
}
