package roomescape.member.repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.member.domain.Member;
import roomescape.member.domain.MemberName;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Member member) {
        String sql = "insert into member (name, email, password) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, member.getName());
            ps.setString(2, member.getEmail());
            ps.setString(3, member.getPassword());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Optional<Member> findById(Long id) {
        String sql = "select id, name, email, password from member where id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, createMemberRowMapper(), id));
        } catch (DataAccessException exception) {
            return Optional.empty();
        }
    }

    public boolean existNameOrEmail(Member member) {
        String sql = """
                select exists (select 1
                from member 
                where name = ? or email = ?)
                """;

        return jdbcTemplate.queryForObject(sql, Boolean.class, member.getName(), member.getEmail());
    }

    private RowMapper<Member> createMemberRowMapper() {
        return (rs, rowNum) -> new Member(
                rs.getLong("id"),
                new MemberName(rs.getString("name")),
                rs.getString("email"),
                rs.getString("password")
        );
    }
}
