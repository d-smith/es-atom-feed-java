package atom;


import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JdbcAtomRepository implements AtomRepository  {
    private String linkURLBase;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private JdbcTemplate jdbcTemplate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

    private PoolDataSource initPoolFromEnv() {
        PoolDataSource pds = PoolDataSourceFactory.getPoolDataSource();
        String dbUser = System.getenv("FEED_DB_USER");
        String dbPassword = System.getenv("FEED_DB_PASSWORD");
        String dbHost = System.getenv("FEED_DB_HOST");
        String dbPort = System.getenv("FEED_DB_PORT");
        String dbService = System.getenv("FEED_DB_SVC");

        String connectUrl = String.format("jdbc:oracle:thin:@//%s:%s/%s",
                dbHost, dbPort, dbService);

        log.info("Connection url is {}", connectUrl);

        try {
            pds = PoolDataSourceFactory.getPoolDataSource();
            pds.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
            pds.setURL(connectUrl);
            pds.setUser(dbUser);
            pds.setPassword(dbPassword);
        } catch(SQLException e) {
            throw new RuntimeException("Unable to initialize connection pool",e);
        }

        return pds;
    }

    public JdbcAtomRepository() throws SQLException {
        linkURLBase = System.getenv("FEED_LINK_URL_BASE");
        if (linkURLBase == null || linkURLBase.equals("")) {
            throw new RuntimeException("FEED_LINK_URL_BASE environment variable not set or empty");
        }

        PoolDataSource poolDataSource = initPoolFromEnv();

        //Make sure we can get a connection from the pool
        try(Connection connection = poolDataSource.getConnection();) {}

        jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(poolDataSource);

    }

    private String currentFeed() {
        return jdbcTemplate.queryForObject("select feedid from feed_state", String.class);
    }

    private String previousFeed(String feedid) {
        return jdbcTemplate.queryForObject("select previous from feeds where feedid = ?",
                String.class, feedid);
    }

    @Override
    public Feed retrieveCurrentFeed() {
        String feedId = currentFeed();
        if(feedId == null || feedId.equals("")) {
            return null;
        }

        Feed feed = new Feed();
        feed.setId(feedId);
        feed.setTitle("Event store feed");
        feed.setUpdated(dateFormat.format(new Date()));

        List<Link> links = new ArrayList<>();
        feed.setLink(links);

        Link self = new Link();
        self.setRel("self");
        self.setHref(String.format("http://%s/notifications/recent", linkURLBase));
        links.add(self);

        Link related = new Link();
        related.setRel("related");
        related.setHref(String.format("http://%s/notifications/%s", linkURLBase,feedId));
        links.add(related);

        String previousFeed = previousFeed(feedId);

        Link previous = new Link();
        previous.setRel("prev-archive");
        previous.setHref(String.format("http://%s/notifications/%s", linkURLBase,previousFeed));
        links.add(previous);

        //TODO - add items that are part of the current feed

        return feed;
    }
}
