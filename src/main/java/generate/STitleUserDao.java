package generate;

import generate.STitleUser;

public interface STitleUserDao {
    int deleteByPrimaryKey(Integer titleUserId);

    int insert(STitleUser record);

    int insertSelective(STitleUser record);

    STitleUser selectByPrimaryKey(Integer titleUserId);

    int updateByPrimaryKeySelective(STitleUser record);

    int updateByPrimaryKey(STitleUser record);
}