library ieee;
use ieee.std_logic_1164.all;

entity si is
	port(a,b,c:in std_logic;
	z:out std_logic);
end entity;

architecture cmp of si is
begin
	process(a,b,c)
	begin
		if(a='1' and b='1' and c='1') then
			z<='1';
		else z<='0';
		end if;
		end process;
	end architecture;
	