library ieee;
use ieee.std_logic_1164.all;

entity sau is
	port(a,b,c:in std_logic;
	z:out std_logic);
end entity;

architecture cmp of sau is
begin
	process(a,b,c)
	begin
		if(a='0' and b='0' and c='0') then
			z<='0';
		else z<='1';
		end if;
		end process;
	end architecture;
	