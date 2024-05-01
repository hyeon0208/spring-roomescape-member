package roomescape.theme.repository;

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
import roomescape.reservation.domain.Name;
import roomescape.theme.domain.Theme;

@Repository
public class ThemeRepository {
    private final JdbcTemplate jdbcTemplate;

    public ThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Theme theme) {
        String sql = "insert into theme(name, description, thumbnail) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    sql, new String[]{"id"}
            );
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDescription());
            ps.setString(3, theme.getThumbnail());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Optional<Theme> findById(Long id) {
        String sql = "select * from theme where id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, createThemeRowMapper(), id));
        } catch (DataAccessException exception) {
            return Optional.empty();
        }
    }

    public List<Theme> findAll() {
        String sql = "select * from theme";
        return jdbcTemplate.query(sql, createThemeRowMapper());
    }

    public void delete(Long id) {
        String sql = "delete from theme where id = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Theme> createThemeRowMapper() {
        return (rs, rowNum) -> {
            return new Theme(
                    rs.getLong("id"),
                    new Name(rs.getString("name")),
                    rs.getString("description"),
                    rs.getString("thumbnail")
            );
        };
    }
}